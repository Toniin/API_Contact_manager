package com.api_contact_manager.services;

import com.api_contact_manager.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.api_contact_manager.models.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
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
