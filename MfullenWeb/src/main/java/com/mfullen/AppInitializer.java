package com.mfullen;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

/**
 *
 * @author mfullen
 */
public class AppInitializer
{
    @Inject
    public AppInitializer(PersistService service)
    {
        service.start();
    }
}
