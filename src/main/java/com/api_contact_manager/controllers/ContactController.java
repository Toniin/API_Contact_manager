package com.api_contact_manager.controllers;

import com.api_contact_manager.models.Contact;
import com.api_contact_manager.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${base.url}")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping(path = "/add")
    public ResponseEntity<?> addContact(@RequestBody Contact contact) {
        contactService.addContact(contact);

        String responseJson = """
                {
                    "message": "Contact added successfully"
                }
                """;

        return ResponseEntity
                .ok(responseJson);
    }

    @GetMapping
    public Iterable<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping(path = "/find/{phoneNumber}")
    public ResponseEntity<?> getContactById(@PathVariable Long phoneNumber) {
        try {
            return ResponseEntity.ok(contactService.getContactById(phoneNumber));
        } catch (Exception e) {
            String errorJson = """
                    {
                        "isError": true,
                        "message":"Contact not found"
                    }
                    """;
            return ResponseEntity.badRequest().body(errorJson);
        }
    }

    @PutMapping(path = "/update/{phoneNumber}")
    public ResponseEntity<?> updateContact(@PathVariable Long phoneNumber, @RequestBody Contact updateContact) {
        contactService.updateContact(phoneNumber, updateContact);
        String responseJson = """
                {
                    "message": "Contact renamed successfully"
                }
                """;

        return ResponseEntity
                .ok(responseJson);
    }

    @DeleteMapping(path = "/delete/{phoneNumber}")
    public ResponseEntity<?> deleteContact(@PathVariable Long phoneNumber) {
        contactService.deleteContact(phoneNumber);

        String responseJson = """
                {
                    "message": "Contact deleted successfully"
                }
                """;

        return ResponseEntity
                .ok(responseJson);
    }
}
