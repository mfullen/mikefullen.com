package com.mfullen.rest.authorization;

import com.sun.jersey.api.container.filter.RolesAllowedResourceFilterFactory;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ResourceFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author mfullen
 */
public class ResourceFilterFactory extends RolesAllowedResourceFilterFactory
{
    private ResourceFilter resourceFilter;

    @Inject
    public ResourceFilterFactory(ResourceFilter resourceFilter)
    {
        this.resourceFilter = resourceFilter;
    }

    @Override
    public List<ResourceFilter> create(AbstractMethod am)
    {
        List<ResourceFilter> filters = super.create(am);
        if (filters == null)
        {
            filters = new ArrayList<>();
        }
        List<ResourceFilter> securityFilters = new ArrayList<>(filters);
        //put the Security Filter first in line
        securityFilters.add(0, resourceFilter);
        return securityFilters;
    }
}
