package com.mfullen.rest.model.mapping;

import org.modelmapper.ModelMapper;

/**
 *
 * @author mfullen
 */
public class RestMapper implements IMappingService
{
    private final ModelMapper mapper = new ModelMapper();

    public RestMapper()
    {
        mapper.addMappings(new BlogMapper());
        mapper.addMappings(new UserMapper());
    }

    @Override
    public ModelMapper getMapper()
    {
        return mapper;
    }
}
