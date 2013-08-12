package com.mfullen.rest.resources.account.integration;

import static org.junit.Assert.*;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.mfullen.model.UserModel;
import com.mfullen.model.VerificationToken;
import com.mfullen.repositories.UserRepository;
import com.mfullen.rest.BaseResourceTest;
import com.mfullen.rest.authorization.AuthenticatedUserToken;
import com.mfullen.rest.authorization.AuthorizationModule;
import com.mfullen.rest.request.CreateUserRequest;
import com.mfullen.rest.resources.AccountResource;
import com.mfullen.rest.resources.ResourceModule;
import com.mfullen.rest.resources.VerificationResource;
import com.mfullen.rest.services.ServiceModule;
import com.mfullen.rest.services.email.EmailGatewayService;
import com.mfullen.rest.services.email.MailServiceModule;
import com.mfullen.rest.services.email.MockEmailGatewayServiceImpl;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.bval.guice.ValidationModule;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author mfullen
 */
public class AccountIntegrationTest extends BaseResourceTest
{

    private UserRepository userRepository;

    @Before
    public void setup() throws Exception
    {
        super.setUp();
        userRepository = injector.getInstance(UserRepository.class);
    }

    @After
    public void tearDown()
    {
    }

    protected String calculateAuthToken(String sessionToken, String stringToHash, String nonce, String currentDate)
    {
        byte[] digest = DigestUtils.sha256(sessionToken + ":" + stringToHash + "," + currentDate + "," + nonce);
        return new String(Base64.encodeBase64(digest));
    }

    @Test
    @Ignore(
    "Need to provide dependency injection to toggle between request signing and Session")
    public void testAuth_RequestSigningAuth()
    {
        final DateTimeFormatter ISO8061_FORMATTER = ISODateTimeFormat.dateTimeNoMillis();
        String currentDate = ISO8061_FORMATTER.print(new DateTime());
        AuthenticatedUserToken token = registerUser();
        WebResource webResource = resource();
        String nonce = RandomStringUtils.randomAlphanumeric(8);
        String authToken = calculateAuthToken(token.getToken(), "account/test" + ",GET", nonce, currentDate);
        ClientResponse response = webResource.path("account").path("test")
                .header("nonce", nonce)
                .header("Authorization", token.getUserId() + ":" + authToken)
                .header("x-java-rest-date", currentDate)
                .get(ClientResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAuth_Session()
    {
        final DateTimeFormatter ISO8061_FORMATTER = ISODateTimeFormat.dateTimeNoMillis();
        String currentDate = ISO8061_FORMATTER.print(new DateTime());
        AuthenticatedUserToken token = registerUser();
        WebResource webResource = resource();
        UserModel findByApiKey = userRepository.findByApiKey(token.getToken());
        VerificationToken verificationToken = findByApiKey.getVerificationTokens().get(0);
        String encodedToken = new String(Base64.encodeBase64(verificationToken.getToken().getBytes()));

        ClientResponse verify = webResource.path("verify").path("tokens").path(encodedToken).post(ClientResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), verify.getStatus());
        //TODO figure out why this was being overriden to false after verifying
        findByApiKey.setVerified(true);
        userRepository.save(findByApiKey);
        String nonce = RandomStringUtils.randomAlphanumeric(8);
        String authToken = token.getToken();
        ClientResponse response = webResource.path("account").path("test")
                .header("nonce", nonce)
                .header("Authorization", authToken)
                .header("x-java-rest-date", currentDate)
                .get(ClientResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testRegister()
    {
        AuthenticatedUserToken token = registerUser();
        assertEquals(1, token.getUserId(), 0);
    }

    public AuthenticatedUserToken registerUser()
    {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("mike@mikefullen.com");
        request.setPassword("hunter2");
        request.setUsername("TheJoker");
        WebResource webResource = resource();
        ClientResponse response = webResource.path("account/register").entity(request, MediaType.APPLICATION_JSON).post(ClientResponse.class);
        //assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        return fromResponse(response);
    }

    protected AuthenticatedUserToken fromResponse(ClientResponse response)
    {
        return response.getEntity(AuthenticatedUserToken.class);
    }

    @Override
    public List<Class> getClasses()
    {
        final List<Class> classes = new ArrayList<>();
        classes.add(AccountResource.class);
        classes.add(VerificationResource.class);
        return classes;
    }

    @Override
    public List<Module> getModules()
    {
        final List<Module> modules = new ArrayList<>();
        modules.add(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                install(new ResourceModule());
                install(new AuthorizationModule());
                install(new ServiceModule());
                install(new ValidationModule());
                install(new MailServiceModule());
                bind(EmailGatewayService.class).to(MockEmailGatewayServiceImpl.class);
            }
        });

        modules.add(new ServletModule()
        {
            @Override
            protected void configureServlets()
            {
                //java persistence
                filter("/*").through(PersistFilter.class);
            }
        });
        modules.add(new JpaPersistModule("rest_test"));
        return modules;
    }
}
