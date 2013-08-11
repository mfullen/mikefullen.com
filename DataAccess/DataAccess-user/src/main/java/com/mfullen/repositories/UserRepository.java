package com.mfullen.repositories;

import com.mfullen.infrastructure.Repository;
import com.mfullen.model.UserModel;

/**
 *
 * @author mfullen
 */
public interface UserRepository extends Repository<UserModel>
{
    UserModel findByEmail(String email);

    UserModel findByUserName(String username);

    UserModel findByApiKey(String apikey);
}
