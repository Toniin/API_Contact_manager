package com.api_contact_manager.services;

import com.api_contact_manager.controllers.ContactController;
import com.api_contact_manager.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.api_contact_manager.models.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//@WebMvcTest(UserService.class)
@SpringBootTest
public class UserServiceTest {
    @MockBean
    private PasswordEncoder passwordEncoderMock;

    @Test
    void UserService_CreateUser_WithPasswordEncoder() {
//        GIVEN
        User user = new User();
        user.setUsername("Test");
        user.setPassword("password");
        user.setRole(USER);

//        WHEN
        when(passwordEncoderMock.encode(user.getPassword())).thenReturn("passwordEncoded");
        user.setPassword(passwordEncoderMock.encode(user.getPassword()));

//        THEN
        assertEquals(user.getUsername(), "Test");
        assertEquals(user.getPassword(), "passwordEncoded");

        System.out.println("Username");
        System.out.println("Expected : Test \nResult :" + user.getUsername());
        System.out.println("Password");
        System.out.println("Expected : passwordEncoded \nResult :" + user.getPassword());
    }
}
