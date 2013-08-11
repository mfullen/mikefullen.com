package com.mfullen.rest.services.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfullen
 */
abstract class AbstractEncryptionService implements EncryptionService
{
    public static final int HASH_ITERATIONS = 1000;
    static final Logger logger = LoggerFactory.getLogger(EncryptionService.class);
    static final String HASH_SALT = "4477441e-cfc0-4bb2-bfbe-e4a19d643a58";

    @Override
    public String hashToken(String token, String salt)
    {
        byte[] hash = getHash(token, salt.getBytes());
        return new Base64(true).encodeToString(hash);
    }

    @Override
    public String hashToken(String token)
    {
        return hashToken(token, HASH_SALT);
    }

    @Override
    public byte[] getHash(String token, byte[] salt)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(salt);
            byte[] input = digest.digest(token.getBytes("UTF-8"));
            for (int i = 0; i < HASH_ITERATIONS; i++)
            {
                digest.reset();
                input = digest.digest(input);
            }
            return input;
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException ex)
        {
            logger.error("Error getting hash");
        }

        return null;
    }
}
