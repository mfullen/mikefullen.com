package com.mfullen.rest.resources;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author mfullen
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AbstractREST
{
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;

    @PostConstruct
    public void init()
    {

    }
}
