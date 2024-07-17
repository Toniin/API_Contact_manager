package com.api_contact_manager.controllers;

import com.api_contact_manager.models.Contact;
import com.api_contact_manager.services.ContactService;
import com.api_contact_manager.services.JwtUtils;
import com.api_contact_manager.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
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
        String contactJson = "{\"name\":\"Test\",\"phoneNumber\":777}";

//        WHEN
        ResultActions response = mockMvc.perform(post("/api/contacts/add")
                .with(csrf().asHeader())
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(contactJson));

//        THEN
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.phoneNumber").value("777"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void ContactController_GetAllContacts_200_Sucess() throws Exception {
//        GIVEN
        Contact contact1 = new Contact();
        contact1.setName("Contact 1");
        contact1.setPhoneNumber(111L);

        Contact contact2 = new Contact();
        contact2.setName("Contact 2");
        contact2.setPhoneNumber(222L);

        List<Contact> contacts = List.of(contact1, contact2);

        when(contactServiceMock.getAllContacts()).thenReturn(contacts);

//        WHEN
        ResultActions response = mockMvc.perform(get("/api/contacts"));

//        THEN
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].phoneNumber").value("111"))
                .andExpect(jsonPath("$[0].name").value("Contact 1"))
                .andExpect(jsonPath("$[1].phoneNumber").value("222"))
                .andExpect(jsonPath("$[1].name").value("Contact 2"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void ContactController_GetContactById_200_Sucess() throws Exception {
//        GIVEN
        Long phoneNumber = 111L;

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
    @WithMockUser(roles = "ADMIN")
    void ContactController_UpdateContact_200_Sucess() throws Exception {
//        GIVEN
        String contactWithUpdateJson = "{\"name\":\"Contact updated\",\"phoneNumber\":111}";
        Long phoneNumber = 111L;

//        WHEN
        ResultActions response = mockMvc.perform(put("/api/contacts/update/{phoneNumber}", phoneNumber)
                .with(csrf().asHeader())
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(contactWithUpdateJson));

//        THEN
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Contact updated"))
                .andExpect(jsonPath("$.phoneNumber").value(phoneNumber))
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
                .andExpect(jsonPath("$").value("Contact : " + phoneNumber + " is deleted successfully"))
                .andDo(print());
    }
}