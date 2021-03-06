package com.mfullen.rest.authorization;

import com.google.inject.Inject;
import com.mfullen.repositories.UserRepository;
import com.mfullen.rest.services.account.UserService;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfullen
 */
@Provider
public class SecurityContextFilter implements ResourceFilter,
                                              ContainerRequestFilter
{
    private static final Logger LOG = LoggerFactory.getLogger(SecurityContextFilter.class);
    protected static final String HEADER_AUTHORIZATION = "Authorization";
    protected static final String HEADER_DATE = "x-java-rest-date";
    protected static final String HEADER_NONCE = "nonce";
    private final AuthorizationService authorizationService;

    @Inject
    public SecurityContextFilter(AuthorizationService authorizationService)
    {
        this.authorizationService = authorizationService;
        //delegateAuthorizationService(userRepository, userService);
    }

    /**
     * If there is an Authorisation header in the request extract the session
     * token and retrieve the user
     *
     * Delegate to the AuthorizationService to validate the request
     *
     * If the request has a valid session token and the user is validated then a
     * user object will be added to the security context
     *
     * Any Resource Controllers can assume the user has been validated and can
     * merely authorize based on the role
     *
     * Resources with
     *
     * @PermitAll annotation do not require an Authorization header but will
     * still be filtered
     *
     * @param request the ContainerRequest to filter
     *
     * @return the ContainerRequest with a SecurityContext added
     */
    @Override
    public ContainerRequest filter(ContainerRequest request)
    {
        String authToken = request.getHeaderValue(HEADER_AUTHORIZATION);
        String requestDateString = request.getHeaderValue(HEADER_DATE);
        String nonce = request.getHeaderValue(HEADER_NONCE);
        AuthorizationRequestContext context = new AuthorizationRequestContext(request.getPath(), request.getMethod(),
                requestDateString, nonce, authToken);
        final PrincipalUser externalUser = authorizationService.authorize(context);
        request.setSecurityContext(new SecurityContextImpl(externalUser));
        return request;
    }

    /**
     * Specify the AuthorizationService that the application should use
     *
     * @param userRepository
     * @param userService
     * @param config
     */
//    private void delegateAuthorizationService(UserRepository userRepository, UserService userService)
//    {
//        boolean requireSignedRequests = false;
//        if (requireSignedRequests)
//        {
//            this.authorizationService = new RequestSigningAuthorizationService(userRepository, userService);
//        }
//        else
//        {
//            this.authorizationService = new SessionTokenAuthorizationService(userRepository);
//        }
//    }
    @Override
    public ContainerRequestFilter getRequestFilter()
    {
        return this;
    }

    @Override
    public ContainerResponseFilter getResponseFilter()
    {
        return null;
    }
}
