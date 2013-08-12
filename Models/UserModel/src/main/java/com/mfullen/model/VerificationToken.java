package com.mfullen.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author mfullen
 */
@Entity
public class VerificationToken extends AbstractModel
{
    private static final int DEFAULT_EXPIRY_TIME_IN_MINS = 60 * 24; //24 hours
    @Column(length = 36)
    private final String token;
    private Timestamp expiryDate;
    @Enumerated(EnumType.STRING)
    private VerificationTokenType tokenType;
    private boolean verified;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    public VerificationToken()
    {
       this(null, VerificationTokenType.emailRegistration, DEFAULT_EXPIRY_TIME_IN_MINS);
    }

    public VerificationToken(UserModel user, VerificationTokenType tokenType, int expirationTimeInMinutes)
    {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        this.tokenType = tokenType;
        this.expiryDate = calculateExpiryDate(expirationTimeInMinutes);
    }

    public VerificationTokenType getTokenType()
    {
        return tokenType;
    }

    public boolean isVerified()
    {
        return verified;
    }

    public void setVerified(boolean verified)
    {
        this.verified = verified;
    }

    public UserModel getUser()
    {
        return user;
    }

    public void setUser(UserModel user)
    {
        this.user = user;
    }

    public Date getExpiryDate()
    {
        return expiryDate;
    }

    public String getToken()
    {
        return token;
    }

    private Timestamp calculateExpiryDate(int expiryTimeInMinutes)
    {
        long expiryTimeMillis = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expiryTimeInMinutes);
        return new Timestamp(expiryTimeMillis);
    }

    public boolean hasExpired()
    {
        //expired if Now > expiry date
        return !getExpiryDate().after(new Timestamp(System.currentTimeMillis()));
    }
}
