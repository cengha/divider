package com.cengha.divider.service;

import com.cengha.divider.model.Role;
import com.cengha.divider.model.User;
import com.cengha.divider.model.UserRole;
import com.cengha.divider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * Created by TRKoseCe on 20.07.2017.
 */
@Service
public class UserService {

    private final UserRepository repository;
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;

    @Value("${app.roles.role_user}")
    private String roleUser;

    @Autowired
    public UserService(UserRepository repository, UserRoleService userRoleService, RoleService roleService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public User findUserByEmail(String email) {
        return repository.findUserByEmail(email).orElse(null);
    }

    @Transactional
    public User registerUser(@Valid User user) {
        return repository.save(user);
    }

    @Transactional
    public User registerAndAuthenticateUser(User user) {
        user.setEnabled(true);
        Role role = roleService.retrieveByName(roleUser);
        user = this.registerUser(user);

        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(user.getId());
        userRoleService.save(userRole);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication authenticated = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        return user;
    }

}
