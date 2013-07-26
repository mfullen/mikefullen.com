package com.mfullen.repositories.jpa;

import com.mfullen.model.UserModel;
import com.mfullen.model.UserModel_;
import com.mfullen.repositories.UserRepository;
import java.util.List;

/**
 *
 * @author mfullen
 */
public class JpaUserRepository extends AbstractJpaRepository<UserModel> implements
        UserRepository
{
    public List<UserModel> findByEmail(String email)
    {
        return this.findByField(UserModel_.email, email);
    }

    public List<UserModel> findByUserName(String username)
    {
        return this.findByField(UserModel_.userName, username);
    }
}
