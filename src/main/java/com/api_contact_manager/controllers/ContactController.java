package com.api_contact_manager.controllers;

import com.api_contact_manager.models.Contact;
import com.api_contact_manager.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping(path="/add")
    public String addContact(@RequestParam int phoneNumber
            , @RequestParam String name) {

        Contact newContact = new Contact();
        newContact.setPhoneNumber(phoneNumber);
        newContact.setName(name);
        contactService.addContact(newContact);

        return "Contact : " + newContact.getPhoneNumber() + " is added successfully";
    }
}
