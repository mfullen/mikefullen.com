package com.mfullen;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see com.mfullen.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
    private final Injector injector;

    @Inject
    public WicketApplication(Injector injector)
    {
        this.injector = injector;
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage()
    {
        return HomePage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init()
    {
        super.init();
        getComponentInstantiationListeners().add(new GuiceComponentInjector(this, injector));
    }
}
