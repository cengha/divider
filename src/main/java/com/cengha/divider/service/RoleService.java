package com.cengha.divider.service;

import com.cengha.divider.model.Role;
import com.cengha.divider.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by TRKoseCe on 20.07.2017.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Transactional
    public Role retrieveByName(String name){
        return repository.findByName(name).orElseThrow(RuntimeException::new);
    }
    @Transactional
    public Set<Role> retrieveByUserId(Long userId){
        return repository.findByUserId(userId);
    }
}
