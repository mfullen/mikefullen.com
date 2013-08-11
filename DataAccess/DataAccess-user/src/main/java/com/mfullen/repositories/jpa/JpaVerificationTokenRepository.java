package com.mfullen.repositories.jpa;

import com.mfullen.model.VerificationToken;
import com.mfullen.model.VerificationToken_;
import com.mfullen.repositories.VerificationTokenRepository;
import java.util.List;

/**
 *
 * @author mfullen
 */
public class JpaVerificationTokenRepository extends AbstractJpaRepository<VerificationToken> implements
        VerificationTokenRepository
{
    public VerificationToken findByToken(String token)
    {
        List<VerificationToken> findByField = this.findByField(VerificationToken_.token, token);
        return getSingle(findByField);
    }

    protected VerificationToken getSingle(List<VerificationToken> models)
    {
        return models.isEmpty() ? null : models.get(0);
    }
}
