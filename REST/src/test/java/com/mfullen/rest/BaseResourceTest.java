package com.mfullen.rest;

import com.mfullen.model.Role;
import com.mfullen.model.UserModel;
import com.mfullen.model.UserRole;
import com.mfullen.rest.model.request.LoginRequest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainer;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author mfullen
 */
public abstract class BaseResourceTest extends MyGuiceJerseyTest
{
    protected final static UserModel TEST_USER;
    protected final static String FIRST_NAME = "Test";
    protected final static String LAST_NAME = "User";
    protected final static String EMAIL_ADDRESS = "test@example.com";
    protected final static String USERNAME = "testuser";
    protected final static String PASSWORD = "password";
    protected final static String API_KEY = "3467fghsfgh54756756";
    public static final String CONFIG_PROPERTY_PACKAGES = "com.sun.jersey.config.property.packages";
    public static final String POJO_MAPPING_FEATURE = "com.sun.jersey.api.json.POJOMappingFeature";
    public static final String REST_RESOURCES = "com.mfullen.rest.resources";
    public static final String REST_PACKAGE = "com.mfullen.rest";

    static
    {
        TEST_USER = new UserModel();
        TEST_USER.setId(90210l);
        TEST_USER.setFirstName(FIRST_NAME);
        TEST_USER.setLastName(LAST_NAME);
        TEST_USER.setUserName(USERNAME);
        TEST_USER.setEmail(EMAIL_ADDRESS);
        TEST_USER.setApiKey(API_KEY);

        // for (Role role : Role.values())

        UserRole userRole = new UserRole();
        userRole.setRole(Role.AUTHENTICATED);
        userRole.setUser(TEST_USER);

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRole);
        TEST_USER.setRoles(userRoles);

    }

    @Override
    protected Client getClient(TestContainer tc, AppDescriptor ad)
    {
        DefaultClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        return Client.create(config);
    }

    protected LoginRequest createLoginRequest()
    {
        LoginRequest request = new LoginRequest();
        request.setUsername(TEST_USER.getUserName());
        request.setPassword(PASSWORD);
        return request;
    }
}
