package com.examples.springboot.bootstrap;

import com.examples.springboot.model.Privilege;
import com.examples.springboot.model.Role;
import com.examples.springboot.model.User;
import com.examples.springboot.service.PrivilegeService;
import com.examples.springboot.service.RoleService;
import com.examples.springboot.service.UserService;
import com.examples.springboot.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Component
@Transactional
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private PrivilegeService privilegeService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPrivilegeService(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //Creating default Roles and Privileges
        createDefaultRolesAndPrivilege();

        //Creating default superusers
        createDefaultSuperusers();
    }

    private void createDefaultSuperusers() {
        Logger.getInstance().getLog(this.getClass()).info("Creating and update superusers [...]");

        //All roles for the superuser
        Collection<Role> superuserRoles = roleService.findAll();

        //Check if the superuser exits
        User user = userService.findByUsername("osvaldo");
        if (user == null) {
            user = new User();
            user.setFirstName("Osvaldo");
            user.setLastName("Fernandez");
            user.setUsername("osvaldo");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setEnabled(false);
            user.setRoles(superuserRoles);
            user = userService.createOrUpdate(user);
        }
    }

    public void createDefaultRolesAndPrivilege() {
        Logger.getInstance().getLog(this.getClass()).info("Creating and update Application Privileges [...]");

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        Logger.getInstance().getLog(this.getClass()).info("Creating and update Application Roles [...]");

        createRoleIfNotFound("ROLE_ADMIN", Arrays.asList(readPrivilege, writePrivilege));
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
    }

    private Role createRoleIfNotFound(String role_user, Collection<Privilege> privileges) {
        Role role = roleService.findByName(role_user);
        if (role == null) {
            role = new Role(role_user);
            role.setPrivileges(privileges);
            roleService.createOrUpdate(role);
        }
        return role;
    }

    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeService.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeService.createOrUpdate(privilege);
        }
        return privilege;
    }
}
