package com.cengha.divider.service;

import com.cengha.divider.model.UserRole;
import com.cengha.divider.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by TRKoseCe on 20.07.2017.
 */
@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository repository;

    void save(UserRole userRole) {
        repository.save(userRole);
    }

    public Iterable<UserRole> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
