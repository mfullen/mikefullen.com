package com.mfullen.rest.services.account;

import com.mfullen.model.Role;
import com.mfullen.model.UserModel;
import com.mfullen.model.UserRole;
import com.mfullen.repositories.UserRepository;
import com.mfullen.rest.authorization.AuthenticatedUserToken;
import com.mfullen.rest.exceptions.AuthenticationException;
import com.mfullen.rest.exceptions.DuplicateUserException;
import com.mfullen.rest.exceptions.UserNotFoundException;
import com.mfullen.rest.request.CreateUserRequest;
import com.mfullen.rest.request.LoginRequest;
import com.mfullen.rest.services.AbstractService;
import com.mfullen.rest.services.encryption.PasswordEncryptionService;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;

/**
 *
 * @author mfullen
 */
class UserServiceImpl extends AbstractService implements UserService
{
    private UserRepository userRepository;
    private PasswordEncryptionService encryptionService;

    @Inject
    public UserServiceImpl(UserRepository userRepository, PasswordEncryptionService passwordEncryptionService)
    {
        this.userRepository = userRepository;
        this.encryptionService = passwordEncryptionService;
    }

    @Override
    public AuthenticatedUserToken login(LoginRequest request)
    {
        validate(request);

        UserModel user = this.userRepository.findByUserName(request.getUsername());

        if (user == null)
        {
            throw new UserNotFoundException();
        }

        String hashString = encryptionService.hashPassword(request.getPassword(), user.getUserName());

        boolean authenticated = user.getPassword().equals(hashString);
        if (authenticated)
        {
            return new AuthenticatedUserToken(user.getId(), user.getApiKey(),user.getUserName());
        }

        throw new AuthenticationException();
    }

    @Override
    public AuthenticatedUserToken register(CreateUserRequest request, Role role)
    {
        validate(request);
        UserModel existingUser = this.userRepository.findByUserName(request.getUsername());

        if (existingUser != null)
        {
            throw new DuplicateUserException();
        }
        UserModel createNewUser = createNewUser(request, role);
        return new AuthenticatedUserToken(createNewUser.getId(), createNewUser.getApiKey(), createNewUser.getUserName());
    }

    protected UserModel createNewUser(CreateUserRequest request, Role... roles)
    {
        UserModel user = new UserModel();
        user.setUserName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encryptionService.hashPassword(request.getPassword(), user.getUserName()));
        user.setApiKey(encryptionService.hashToken(UUID.randomUUID().toString()));

        Set<UserRole> userRoles = new HashSet<>();

        for (Role role : roles)
        {
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            userRoles.add(userRole);
        }

        user.setRoles(userRoles);

        return userRepository.save(user);
    }
}
