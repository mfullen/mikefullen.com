package com.mfullen.rest.resources;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author mfullen
 */
@Produces(
{
    MediaType.APPLICATION_JSON//, MediaType.APPLICATION_XML
})
@Consumes(MediaType.APPLICATION_JSON)
public class AbstractREST
{
//    @Inject
//    private HttpServletRequest request;
//    @Inject
//    private HttpServletResponse response;
    @PostConstruct
    public void init()
    {
    }
}
