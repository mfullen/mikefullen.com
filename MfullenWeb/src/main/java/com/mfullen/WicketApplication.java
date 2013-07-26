package com.mfullen;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see com.mfullen.Start#main(String[])
 */
@Component
public class WicketApplication extends WebApplication implements
        ApplicationContextAware
{
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

    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws
            BeansException
    {

        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ac));
    }
}
