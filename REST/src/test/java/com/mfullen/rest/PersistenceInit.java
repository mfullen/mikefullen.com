package com.mfullen.rest;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

/**
 *
 * @author mfullen
 */
public class PersistenceInit
{
    @Inject
    PersistenceInit(PersistService service)
    {
        service.start();
    }
}
