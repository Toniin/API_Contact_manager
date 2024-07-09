package com.api_contact_manager.controllers;

import com.api_contact_manager.models.Contact;
import com.api_contact_manager.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping(path="/add")
    public Contact addContact(@RequestBody Contact contact) {
        contactService.addContact(contact);

        return contact;
    }

    @GetMapping
    public Iterable<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping(path="/find/{phoneNumber}")
    public Contact getContactById(@PathVariable Long phoneNumber) {
        return contactService.getContactById(phoneNumber);
    }

    @DeleteMapping(path="/delete/{phoneNumber}")
    public String deleteContact(@PathVariable Long phoneNumber) {
        contactService.deleteContact(phoneNumber);

        return "Contact : " + phoneNumber + " is deleted successfully";
    }

    @PutMapping(path="/update/{phoneNumber}")
    public Contact updateContact(@PathVariable Long phoneNumber, @RequestBody Contact updateContact) {
        contactService.updateContact(phoneNumber, updateContact);

        return updateContact;
    }
}
