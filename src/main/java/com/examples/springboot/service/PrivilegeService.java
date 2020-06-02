package com.examples.springboot.service;

import com.examples.springboot.model.Privilege;
import com.examples.springboot.repository.PrivilegeRepository;
import com.examples.springboot.service.common.ICrudOperation;
import com.examples.springboot.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class PrivilegeService implements ICrudOperation<Privilege, Long> {

    private PrivilegeRepository privilegeRepository;

    @Autowired
    public void setPrivilegeRepository(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Privilege createOrUpdate(Privilege entity) {
        return privilegeRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Privilege privilege = privilegeRepository.findById(id).orElse(null);
        if (privilege != null) {
            privilegeRepository.delete(privilege);
        } else {
            Logger.getInstance().getLog(UserService.class).error("This privilege not exists...!");
        }
    }

    @Override
    public Collection<Privilege> findAll() {
        return privilegeRepository.findAll();
    }

    @Override
    public Privilege findById(Long id) {
        return privilegeRepository.findById(id).orElse(null);
    }

    public Privilege findByName(String name) { return privilegeRepository.findByName(name); }
}
