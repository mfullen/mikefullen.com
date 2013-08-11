package com.mfullen.rest.services.encryption;

/**
 *
 * @author mfullen
 */
public interface EncryptionService
{
    String hashToken(String token, String salt);

    String hashToken(String token);

    byte[] getHash(String token, byte[] salt);
}
