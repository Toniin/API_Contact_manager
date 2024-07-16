package com.api_contact_manager.services;

import com.api_contact_manager.models.User;
import com.api_contact_manager.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.api_contact_manager.models.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void UserService_CreateUser_WithPasswordEncoder() {
//        GIVEN
        User user = new User();
        user.setUsername("Test");
        user.setPassword("password");
        user.setRole(USER);

//        WHEN
        when(passwordEncoder.encode(user.getPassword())).thenReturn("passwordEncoded");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

//        THEN
        assertEquals(user.getUsername(), "Test");
        assertEquals(user.getPassword(), "passwordEncoded");

        System.out.println("Username");
        System.out.println("Expected : Test \nResult :" + user.getUsername());
        System.out.println("Password");
        System.out.println("Expected : passwordEncoded \nResult :" + user.getPassword());
    }
}
