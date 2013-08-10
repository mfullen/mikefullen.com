package com.mfullen.rest;

import static org.junit.Assert.*;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.mfullen.model.Blog;
import com.mfullen.repositories.BlogRepository;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.Timestamp;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author mfullen
 */
@Ignore
public class RestServerTest
{
    static final URI BASE_URI = getBaseURI();
    HttpServer server;

    private static URI getBaseURI()
    {
        return UriBuilder.fromUri("http://localhost/").port(9998).build();
    }

    @Before
    public void startServer() throws IOException
    {
        System.out.println("Starting grizzly...");

        Injector injector = Guice.createInjector(
                new JpaPersistModule("test"),
                new RestApplicationServletModule(), new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(PersistenceInit.class);
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
        blogRepository.save(blog);


    }

    @After
    public void stopServer()
    {
        server.stop();
    }

    @Test
    @Ignore
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

    @Ignore
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

    public static void createBlogRequest(String title)
    {
        String urlParameters = "{\"title\":\"" + title + "\" }";
        String request = "http://localhost:9998/rest/blogs/create";
        InputStream in = null;

        try
        {
            Client client = Client.create();

            WebResource webResource = client
                    .resource(request);

            String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";

            ClientResponse response = webResource.type("application/json")
                    .post(ClientResponse.class, urlParameters);

            if (response.getStatus() != 201)
            {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Output from Server .... \n");
            String output = response.getEntity(String.class);
            System.out.println(output);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception
    {
        RestServerTest test = new RestServerTest();
        test.startServer();
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (!(input = br.readLine()).equalsIgnoreCase("quit"))
        {
            if (input.equalsIgnoreCase("send"))
            {
                createBlogRequest("LOLOLOLOL");
            }
        }
        // hit enter to stop the server
        test.server.stop();
    }
}
