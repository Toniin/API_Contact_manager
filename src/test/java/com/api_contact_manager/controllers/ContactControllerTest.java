package com.api_contact_manager.controllers;

import com.api_contact_manager.models.Contact;
import com.api_contact_manager.services.ContactService;
import com.api_contact_manager.services.JwtUtils;
import com.api_contact_manager.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContactService contactServiceMock;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtUtils jwtUtils;

    @Test
    @WithMockUser(roles = "ADMIN")
    void ContactController_AddContact_200_Sucess() throws Exception {
//        GIVEN
        String contactJson = """
                {
                    "name": "Test",
                    "phoneNumber": 777
                }
                """;

//        WHEN
        ResultActions response = mockMvc.perform(post("/api/contacts/add")
                .with(csrf().asHeader())
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(contactJson));

//        THEN
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Contact added successfully"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void ContactController_GetAllContacts_200_Sucess() throws Exception {
//        GIVEN
        Contact contact1 = new Contact();
        contact1.setName("Contact 1");
        contact1.setPhoneNumber("(+33)1 23 45 67 89");

        Contact contact2 = new Contact();
        contact2.setName("Contact 2");
        contact2.setPhoneNumber("(+33)2 34 56 78 91");

        List<Contact> contacts = List.of(contact1, contact2);

        when(contactServiceMock.getAllContacts()).thenReturn(contacts);

//        WHEN
        ResultActions response = mockMvc.perform(get("/api/contacts"));

//        THEN
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].phoneNumber").value("(+33)1 23 45 67 89"))
                .andExpect(jsonPath("$[0].name").value("Contact 1"))
                .andExpect(jsonPath("$[1].phoneNumber").value("(+33)2 34 56 78 91"))
                .andExpect(jsonPath("$[1].name").value("Contact 2"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void ContactController_GetContactById_200_Sucess() throws Exception {
//        GIVEN
        String phoneNumber = "(+33)1 23 45 67 89";

        Contact contact1 = new Contact();
        contact1.setName("Contact 1");
        contact1.setPhoneNumber(phoneNumber);

        when(contactServiceMock.getContactById(phoneNumber)).thenReturn(contact1);

//        WHEN
        ResultActions response = mockMvc.perform(get("/api/contacts/find/{phoneNumber}", phoneNumber));

//        THEN
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value(phoneNumber))
                .andExpect(jsonPath("$.name").value("Contact 1"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void ContactController_GetContactById_400_ContactNotFound() throws Exception {
//        GIVEN
        String phoneNumber = "(+33)1 23 45 67 89";

        Contact contact1 = new Contact();
        contact1.setName("Contact 1");
        contact1.setPhoneNumber(phoneNumber);

        when(contactServiceMock.getContactById(phoneNumber)).thenThrow(BadCredentialsException.class);

//        WHEN
        ResultActions response = mockMvc.perform(get("/api/contacts/find/{phoneNumber}", phoneNumber));

//        THEN
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isError").value(true))
                .andExpect(jsonPath("$.message").value("Contact not found"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void ContactController_UpdateContact_200_Sucess() throws Exception {
//        GIVEN
        Long phoneNumber = 111L;

        String responseJson = """
                {
                    "message": "Contact renamed successfully"
                }
                """;

//        WHEN
        ResultActions response = mockMvc.perform(put("/api/contacts/update/{phoneNumber}", phoneNumber)
                .with(csrf().asHeader())
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(responseJson));

//        THEN
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Contact renamed successfully"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void ContactController_DeleteContact_200_Sucess() throws Exception {
//        GIVEN
        Long phoneNumber = 111L;

//        WHEN
        ResultActions response = mockMvc.perform(delete("/api/contacts/delete/{phoneNumber}", phoneNumber)
                .with(csrf().asHeader()));

//        THEN
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Contact deleted successfully"))
                .andDo(print());
    }
}