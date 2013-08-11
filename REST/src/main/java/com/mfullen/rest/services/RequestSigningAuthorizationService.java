package com.mfullen.rest.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mfullen.model.UserModel;
import com.mfullen.repositories.UserRepository;
import com.mfullen.rest.security.AuthorizationRequestContext;
import com.mfullen.rest.security.PrincipalUser;
import com.mfullen.rest.security.PrincipalUserImpl;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Any Resource that requires a role must have a header property of the
 * following format:
 * <p/>
 * <
 * code> Authorization: <uuid of user>:<Signed Request>
 * </code>
 * <p/>
 * The signed request hash is comprised of the session token + : + the relative
 * url + , + the Http method + , + Date + , + nonce This string is then Sha-256
 * encoded and then Base64 encoded
 * <p/>
 * An example:
 * <code>
 * Example:
 * 9fbc6f9a-af1b-4767-a492-c8462fd2a4d9:user/2e2ce9e8-798e-42b6-9326-fd2e56aef7aa/cards,POST,2012-06-30T12:00:00+01:00,34e321a7c4
 * <p/>
 * </code>
 * <p/>
 * This will be SHA-256 hashed and then Base64 encoded to produce:
 * <p/>
 * <
 * code> HR/3DJp8RCGo50Wu+/3cr7ibdoNXKg1eYMt3HO5QoP4=
 * </code>
 * <p/>
 * Authorization:
 * 2e2ce9e8-798e-42b6-9326-fd2e56aef7aa:HR/3DJp8RCGo50Wu+/3cr7ibdoNXKg1eYMt3HO5QoP4=
 *
 * @author: Iain Porter
 */
public class RequestSigningAuthorizationService implements AuthorizationService
{
    private static final DateTimeFormatter ISO8061_FORMATTER = ISODateTimeFormat.dateTimeNoMillis();
    public static final int SESSION_DATE_OFFSET = 10;
    Logger LOG = LoggerFactory.getLogger(RequestSigningAuthorizationService.class);
    /**
     * If the nonce already exists in the cache the difference between its
     * timestamp and the current time will be greater than this value
     */
    private static final int NONCE_CHECK_TOLERANCE_IN_MILLIS = 20;
    /**
     * Maximum Number of Nonce values in the cache The capacity will never be
     * reached as long as the number of requests is below this value within the
     * time range specified by ApplicationConfig.getSessionDateOffsetInMinutes()
     */
    private static final int NONCE_CACHE_SIZE = 10000;
    /**
     * A an expiry cache that evicts nonce values after a configurable time
     * period
     */
    private LoadingCache<String, Nonce> nonceCache;
    /**
     * User service required for persisting user objects
     */
    private UserService userService;
    /**
     * directly access user objetcs
     */
    private final UserRepository userRepository;

    @Inject
    public RequestSigningAuthorizationService(UserRepository userRepository, UserService userService)
    {
        this.userService = userService;
        this.userRepository = userRepository;
        initNonceCache();
    }

    private void initNonceCache()
    {
        nonceCache = CacheBuilder.newBuilder()
                .maximumSize(NONCE_CACHE_SIZE)
                .expireAfterWrite(SESSION_DATE_OFFSET, TimeUnit.MINUTES)
                .build(
                new CacheLoader<String, Nonce>()
                {
                    @Override
                    public Nonce load(String key) throws Exception
                    {
                        return new Nonce(new DateTime(), key);
                    }
                });

    }

    /**
     * If the request contains values in the AuthorizationRequestContext attempt
     * to find and validate a user
     *
     * @param context
     * @return The request signature was valid and a user is returned or null if
     * the context did not contain the information necessary to load a user
     */
    @Override
    public PrincipalUser authorize(AuthorizationRequestContext context)
    {
        PrincipalUser externalUser = null;
        if (context.getAuthorizationToken() != null && context.getRequestDateString() != null && context.getNonceToken() != null)
        {
            String[] token = context.getAuthorizationToken().split(":");
            if (token.length == 2)
            {
                Long userId = Long.getLong(token[0]);
                String hashedToken = token[1];
                //make sure date and nonce is valid
                validateRequestDate(context.getRequestDateString());
                validateNonce(context.getNonceToken());

                UserModel user = userRepository.getById(userId);
                if (user != null)
                {
                    externalUser = new PrincipalUserImpl(user);
                    if (!isAuthorized(user, context, hashedToken))
                    {
                        throw new NullPointerException("Request rejected due to an authorization failure");
                    }
                }
            }
        }
        return externalUser;
    }

    /**
     * Authorize a hashed token against a request string The hashed token will
     * be comprised of: the User's session token + the relative request Url +
     * the Http Verb + the Date as ISO 8061 String + a nonce token generated by
     * the client
     * <code>
     * Example:
     * 9fbc6f9a-af1b-4767-a492-c8462fd2a4d9:user/2e2ce9e8-798e-42b6-9326-fd2e56aef7aa,GET,2012-06-30T12:00:00+01:00,34e321a7c4
     * <p/>
     * </code>
     * <p/>
     * This will be SHA-256 hashed and then Base64 encoded to produce:
     * <p/>
     * <
     * code> HR/3DJp8RCGo50Wu+/3cr7ibdoNXKg1eYMt3HO5QoP4=
     * </code>
     *
     * @param user should have a session token that will validate the request
     * signature
     * @param authorizationRequest the request containing all the details needed
     * to authorize the request
     * @param hashedToken the token to match against
     * @return true if the token is authorized
     */
    private boolean isAuthorized(UserModel user, AuthorizationRequestContext authorizationRequest, String hashedToken)
    {
        //Assert.notNull(user);
        //Assert.notNull(authorizationRequest.getAuthorizationToken());
        String unEncodedString = composeUnEncodedRequest(authorizationRequest);

        String userTokenHash = encodeAuthToken(user.getApiKey(), unEncodedString);
        if (hashedToken.equals(userTokenHash))
        {
            //token.setLastUpdated(new Date());
            //userRepository.save(user);
            return true;
        }

        LOG.error("Hash check failed for hashed token: {} for the following request: {} for user: {}",
                new Object[]
                {
                    authorizationRequest.getAuthorizationToken(),
                    unEncodedString, user.getId()
                });
        return false;
    }

    /**
     * Encode the token by prefixing it with the User's Session Token
     *
     * @param token
     * @return encoded token
     */
    private String encodeAuthToken(String token, String unencodedRequest)
    {
        byte[] digest = DigestUtils.sha256(token + ":" + unencodedRequest);
        return new String(Base64.encodeBase64(digest));

    }

    /**
     * The recipe to compose a signed request
     *
     * @param authRequest
     * @return the string value to hash
     */
    private String composeUnEncodedRequest(AuthorizationRequestContext authRequest)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(authRequest.getRequestUrl());
        sb.append(',');
        sb.append(authRequest.getHttpMethod().toUpperCase());
        sb.append(',');
        sb.append(authRequest.getRequestDateString());
        sb.append(',').append(authRequest.getNonceToken());
        return sb.toString();

    }

    /**
     * Ensure that the date of the request falls within the configured range
     *
     * @param requestDateString
     */
    private void validateRequestDate(String requestDateString)
    {
        Date date = getDateFromIso8061DateString(requestDateString);
        DateTime now = new DateTime();
        DateTime offset = new DateTime(date);
        if (!(offset.isAfter(now.minusMinutes(SESSION_DATE_OFFSET))
                && offset.isBefore(now.plusMinutes(SESSION_DATE_OFFSET))))
        {
            LOG.error("Date in header is out of range: {}", requestDateString);
            throw new NullPointerException("Date in header is out of range: " + requestDateString);
        }
    }

    /**
     * The nonce value sent by the client and used in the request signature
     * should be unique across the system Nonce values will only be considered
     * unique within the time limits of the cache. The value will be protected
     * if the cache expiry time is within the limits of the request date range.
     * If the date in the request is stale then the nonce value wil be
     * irrelevant
     *
     * Note that the caching strategy will not work in a cluster. A distributed
     * cache will be needed.
     *
     * @param nonceValue
     */
    private void validateNonce(String nonceValue)
    {
        Nonce nonce = nonceCache.getUnchecked(nonceValue);
        Duration tolerance = new Duration(nonce.timestamp, new DateTime());
        if (tolerance.isLongerThan(Duration.millis(NONCE_CHECK_TOLERANCE_IN_MILLIS)))
        {
            LOG.error("Nonce value was not unique: {}", nonceValue);
            throw new NullPointerException("Nonce value is not unique");
        }
    }

    private static class Nonce
    {
        private DateTime timestamp;
        private String nonceValue;

        Nonce(DateTime time, String nonce)
        {
            this.timestamp = time;
            this.nonceValue = nonce;
        }
    }

    public static Date getDateFromIso8061DateString(String dateString)
    {
        return ISO8061_FORMATTER.parseDateTime(dateString).toDate();
    }

    public static String getCurrentDateAsIso8061String()
    {
        DateTime today = new DateTime();
        return ISO8061_FORMATTER.print(today);
    }

    public static String getDateDateAsIso8061String(DateTime date)
    {
        return ISO8061_FORMATTER.print(date);
    }
}
