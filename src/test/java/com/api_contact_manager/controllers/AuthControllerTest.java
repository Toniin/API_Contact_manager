package com.api_contact_manager.controllers;

import com.api_contact_manager.dto.AuthenticateDTO;
import com.api_contact_manager.filters.JwtFilter;
import com.api_contact_manager.models.User;
import com.api_contact_manager.services.AuthService;
import com.api_contact_manager.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static com.api_contact_manager.models.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userServiceMock;
    @MockBean
    private AuthService authService;
    @MockBean
    private JwtFilter jwtFilter;

    @Test
    public void AuthController_Register_200_Sucess() throws Exception {
//        GIVEN
        User newUser = new User();
        newUser.setUsername("Test");
        newUser.setPassword("password");
        newUser.setRole(USER);

        when(userServiceMock.isUserExists(newUser.getUsername())).thenReturn(false);
        when(userServiceMock.createUser(newUser)).thenReturn(newUser);

        String userJson = "{\"username\":\"Test\"," +
                "\"password\":\"password\"," +
                "\"role\":\"USER\"}";

//        WHEN
        ResultActions response = mockMvc.perform(post("/api/contacts/auth/register")
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(userJson));

//        THEN
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Test"))
                .andExpect(jsonPath("$.password").value("password"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andDo(print());
    }

    @Test
    public void AuthController_Register_400_UserAlreadyExits() throws Exception {
        //        GIVEN
        User newUser = new User();
        newUser.setUsername("Test");
        newUser.setPassword("password");
        newUser.setRole(USER);

        when(userServiceMock.isUserExists(newUser.getUsername())).thenReturn(true);

        String userJson = "{\"username\":\"Test\"," +
                "\"password\":\"password\"," +
                "\"role\":\"USER\"}";

//        WHEN
        ResultActions response = mockMvc.perform(post("/api/contacts/auth/register")
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(userJson));

//        THEN
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void AuthController_Register_400_ROLE_UNKNOWN() throws Exception {
        //        GIVEN
        String userJson = "{\"username\":\"Test\"," +
                "\"password\":\"password\"," +
                "\"role\":\"ROLE_UNKNOWN\"}";

//        WHEN
        ResultActions response = mockMvc.perform(post("/api/contacts/auth/register")
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(userJson));

//        THEN
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void AuthController_login_200_Authenticated() throws Exception {
//        GIVEN
        AuthenticateDTO authenticateDTO = new AuthenticateDTO("Test", "password");

        when(authService.authenticateUser(authenticateDTO)).thenReturn(Map.of("username", authenticateDTO.username(), "token", "Bearer token"));

        String bodyRequest = objectMapper.writeValueAsString(authenticateDTO);

//        WHEN
        ResultActions response = mockMvc.perform(post("/api/contacts/auth/login")
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(bodyRequest));

        //        THEN
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Test"))
                .andExpect(jsonPath("$.token").value("Bearer token"))
                .andDo(print());
    }

    @Test
    void AuthController_login_400_BadCredentialsException() throws Exception {
//        GIVEN
        AuthenticateDTO authenticateDTO = new AuthenticateDTO("Test", "password");

        when(authService.authenticateUser(authenticateDTO)).thenThrow(BadCredentialsException.class);

        String bodyRequest = objectMapper.writeValueAsString(authenticateDTO);

//        WHEN
        ResultActions response = mockMvc.perform(post("/api/contacts/auth/login")
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(bodyRequest));

//        THEN
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Invalid username or password"))
                .andDo(print());
    }
}