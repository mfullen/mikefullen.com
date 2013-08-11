package com.mfullen.rest.services.encryption;

/**
 *
 * @author mfullen
 */
public interface PasswordEncryptionService extends EncryptionService
{
    /**
     * Hash the password using salt values See
     * https://www.owasp.org/index.php/Hashing_Java
     *
     * @param passwordToHash
     * @return hashed password
     */
    String hashPassword(String password, String uniqueId);
}
