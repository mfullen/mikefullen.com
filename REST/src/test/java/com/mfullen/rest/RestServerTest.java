package com.mfullen.rest;

import static org.junit.Assert.*;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.RequestScoped;
import com.mfullen.model.Blog;
import com.mfullen.repositories.BlogRepository;
import com.mfullen.repositories.UserRepository;
import com.mfullen.repositories.jpa.JpaBlogRepository;
import com.mfullen.repositories.jpa.JpaUserRepository;
import com.mfullen.rest.model.mapping.IMappingService;
import com.mfullen.rest.model.mapping.RestMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mfullen
 */
public class RestServerTest
{
    static final URI BASE_URI = getBaseURI();
    HttpServer server;

    private static URI getBaseURI()
    {
        return UriBuilder.fromUri("http://localhost/").port(9998).build();
    }

    static class PersistenceInit
    {
        @Inject
        PersistenceInit(PersistService service)
        {
            service.start();
        }
    }

    @Before
    public void startServer() throws IOException
    {
        System.out.println("Starting grizzly...");

        Injector injector = Guice.createInjector(new JerseyServletModule()
        {
            @Override
            protected void configureServlets()
            {
                install(new JpaPersistModule("test"));
                bind(IMappingService.class).to(RestMapper.class);
                bind(BlogRepository.class).to(JpaBlogRepository.class);
                bind(UserRepository.class).to(JpaUserRepository.class).in(RequestScoped.class);

                //bind(GuiceFilter.class);
                //bind(GuiceContainer.class);
                // hook Jackson into Jersey as the POJO <-> JSON mapper
                bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

                final Map<String, String> params = new HashMap<String, String>();
                params.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
                params.put("com.sun.jersey.config.property.packages", "com.mfullen.rest.resources");
                serve("/rest/*").with(GuiceContainer.class, params);
            }
        });

        injector.getInstance(PersistenceInit.class);

        ResourceConfig rc = new PackagesResourceConfig("com.mfullen.rest");
        IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory(rc, injector);
        server = GrizzlyServerFactory.createHttpServer(BASE_URI + "rest/", rc, ioc);

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sservices/application.wadl\nTry out %s{app_name}\nHit enter to stop it...",
                BASE_URI, BASE_URI));

        BlogRepository blogRepository = injector.getInstance(BlogRepository.class);
        Blog blog = new Blog();
        blog.setTitle("The best blog");
        blog.setDatePosted(new Timestamp(System.currentTimeMillis()));
        blogRepository.add(blog);


    }

    @After
    public void stopServer()
    {
        server.stop();
    }

    @Test
    public void testGetAll() throws IOException
    {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI());

        ClientResponse resp = service.path("rest").path("blogs")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got stuff: " + resp);
        String text = resp.getEntity(String.class);

        assertEquals(200, resp.getStatus());
//        assertEquals("<h2>All stuff</h2><ul>"
//                + "<li>stuff1</li>"
//                + "<li>stuff2</li>"
//                + "<li>stuff3</li></ul>", text);

    }

    @Test
    public void testGetById() throws IOException
    {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI());

        ClientResponse resp = service.path("rest")
                .path("blogs").path("1")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got stuff: " + resp);
        String text = resp.getEntity(String.class);

        assertEquals(200, resp.getStatus());
        //assertEquals("<html><body><div>stuff1</div></body></html>", text);

        ClientResponse resp2 = service.path("rest")
                .path("blogs").path("22")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        String text2 = resp2.getEntity(String.class);

        assertEquals(404, resp2.getStatus());
        //assertEquals("<html><body><div>Not Found</div></body></html>", text2);

    }

    public static void main(String[] args) throws Exception
    {
        RestServerTest test = new RestServerTest();
        test.startServer();
        System.in.read(); // hit enter to stop the server
        test.server.stop();
    }
}
