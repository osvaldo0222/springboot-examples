package com.examples.springboot.service;

import com.examples.springboot.model.Role;
import com.examples.springboot.repository.RoleRepository;
import com.examples.springboot.service.common.ICrudOperation;
import com.examples.springboot.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class RoleService implements ICrudOperation<Role, Long> {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createOrUpdate(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            roleRepository.delete(role);
        } else {
            Logger.getInstance().getLog(UserService.class).error("This role not exists...!");
        }
    }

    @Override
    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role findByName(String name) { return roleRepository.findByName(name); }
}
