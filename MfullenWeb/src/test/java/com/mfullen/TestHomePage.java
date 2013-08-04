package com.mfullen;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
    private WicketTester tester;

    @Before
    public void setUp()
    {
        Injector injector = Guice.createInjector(new ServletModule()
        {
            @Override
            protected void configureServlets()
            {
                install(new MfullenModule());
                install(new JpaPersistModule("test"));
            }
        });
        injector.getInstance(PersistService.class).start();
        tester = new WicketTester(injector.getInstance(WebApplication.class));
    }

    @Test
    public void homepageRendersSuccessfully()
    {
        //start and render the test page
        tester.startPage(HomePage.class);

        //assert rendered page class
        tester.assertRenderedPage(HomePage.class);
    }
}
