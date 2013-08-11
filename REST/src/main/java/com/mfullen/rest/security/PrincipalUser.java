package com.mfullen.rest.security;

import java.security.Principal;
import java.util.Collection;

/**
 *
 * @author mfullen
 */
public interface PrincipalUser extends Principal
{
    Collection<String> getRoles();
}
