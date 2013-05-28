package com.mfullen.rest;

import com.mfullen.rest.resources.BlogResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author mfullen
 */
@ApplicationPath("/rest")
public class RESTApplication extends Application
{

    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> classes = new HashSet<Class<?>>();

        classes.add(BlogResource.class);

        return classes;
    }

}
