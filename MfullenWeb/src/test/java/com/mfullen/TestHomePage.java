package com.mfullen;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Simple test using the WicketTester
 */
@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "/applicationContext.xml" and "/applicationContext-test.xml"
// in the root of the classpath
@ContextConfiguration(locations =
{
    "/applicationContext-test.xml"
})
@TransactionConfiguration(transactionManager = "transactionManager",
                          defaultRollback = false)
public class TestHomePage
{
    private WicketTester tester;
    @Autowired
    private WebApplication myWicketApplication;

    @Before
    public void setUp()
    {
        tester = new WicketTester(myWicketApplication);
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
