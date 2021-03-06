package com.mfullen.rest.services.encryption;

/**
 *
 * @author mfullen
 */
class PasswordEncryptionServiceImpl extends AbstractEncryptionService implements
        PasswordEncryptionService
{
    @Override
    public String hashPassword(String password, String uniqueId)
    {
        return hashToken(password, HASH_SALT);
    }
}
