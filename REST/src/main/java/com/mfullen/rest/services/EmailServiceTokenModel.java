package com.mfullen.rest.services;

import com.mfullen.model.UserModel;
import com.mfullen.model.VerificationToken;
import com.mfullen.model.VerificationTokenType;
import java.io.Serializable;
import org.apache.commons.codec.binary.Base64;

public class EmailServiceTokenModel implements Serializable
{
    private final String emailAddress;
    private final String token;
    private final VerificationTokenType tokenType;
    private final String hostNameUrl;

    public EmailServiceTokenModel(UserModel user, VerificationToken token, String hostNameUrl)
    {
        this.emailAddress = user.getEmail();
        this.token = token.getToken();
        this.tokenType = token.getTokenType();
        this.hostNameUrl = hostNameUrl;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public String getEncodedToken()
    {
        return new String(Base64.encodeBase64(token.getBytes()));
    }

    public String getToken()
    {
        return token;
    }

    public VerificationTokenType getTokenType()
    {
        return tokenType;
    }

    public String getHostNameUrl()
    {
        return hostNameUrl;
    }
}
