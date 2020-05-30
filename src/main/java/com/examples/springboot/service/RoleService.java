package com.examples.springboot.service;

import com.examples.springboot.model.Role;
import com.examples.springboot.repository.RoleRepository;
import com.examples.springboot.service.common.ICrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

}
