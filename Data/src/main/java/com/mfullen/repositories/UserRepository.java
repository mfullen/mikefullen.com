package com.mfullen.repositories;

import com.mfullen.infrastructure.Repository;
import com.mfullen.model.UserModel;
import java.util.List;

/**
 *
 * @author mfullen
 */
public interface UserRepository extends Repository<UserModel>
{
    List<UserModel> findByEmail(String email);

    List<UserModel> findByUserName(String username);
}