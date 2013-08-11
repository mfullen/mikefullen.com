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
    public UserModel findByEmail(String email)
    {
        List<UserModel> findByField = this.findByField(UserModel_.email, email);
        return getSingle(findByField);
    }

    public UserModel findByUserName(String username)
    {
        List<UserModel> findByField = this.findByField(UserModel_.userName, username);
        return getSingle(findByField);
    }

    public UserModel findByApiKey(String apikey)
    {
        List<UserModel> findByField = this.findByField(UserModel_.apiKey, apikey);
        return getSingle(findByField);
    }

    protected UserModel getSingle(List<UserModel> models)
    {
        return models.isEmpty() ? null : models.get(0);
    }
}
