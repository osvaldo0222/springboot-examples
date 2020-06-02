package com.examples.springboot;

import com.examples.springboot.model.User;
import com.examples.springboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        User user = new User();

        user.setFirstName("Osvaldo");
        user.setLastName("Fernandez");
        user.setUsername("osvaldof22@hotmail.com");
        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode("admin"));

        User result = userService.createOrUpdate(user);

        assertTrue("Usuario creado" ,result.getPassword().equals(user.getPassword()));
    }

}
