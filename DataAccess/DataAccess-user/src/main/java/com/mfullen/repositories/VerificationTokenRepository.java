package com.mfullen.repositories;

import com.mfullen.infrastructure.Repository;
import com.mfullen.model.VerificationToken;

/**
 *
 * @author mfullen
 */
public interface VerificationTokenRepository extends
        Repository<VerificationToken>
{
    VerificationToken findByToken(String token);
}
