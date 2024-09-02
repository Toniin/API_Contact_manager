package com.api_contact_manager.services;

import com.api_contact_manager.configuration.WebConfig;
import com.api_contact_manager.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.api_contact_manager.models.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
//    @Mock
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebConfig webConfigMock;

    @Test
    void UserService_CreateUser_WithPasswordEncoder() {
//        GIVEN
        User user = new User();
        user.setUsername("Test");
        user.setPassword("password");
        user.setRole(USER);

//        WHEN
        when(webConfigMock.passwordEncoder().encode(user.getPassword())).thenReturn("passwordEncoded");
        user.setPassword(webConfigMock.passwordEncoder().encode(user.getPassword()));

//        THEN
        assertEquals(user.getUsername(), "Test");
        assertEquals(user.getPassword(), "passwordEncoded");

        System.out.println("Username");
        System.out.println("Expected : Test \nResult :" + user.getUsername());
        System.out.println("Password");
        System.out.println("Expected : passwordEncoded \nResult :" + user.getPassword());
    }
}
