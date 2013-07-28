package com.mfullen.rest.model.mapping;

import com.mfullen.model.UserModel;
import com.mfullen.rest.model.RestUser;
import org.modelmapper.PropertyMap;

/**
 *
 * @author mfullen
 */
public class UserMapper extends PropertyMap<UserModel, RestUser>
{
    @Override
    protected void configure()
    {
        map().setId(source.getId());
        map().setUsername(source.getUserName());
    }
}
