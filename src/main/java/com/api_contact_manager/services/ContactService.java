package com.api_contact_manager.services;

import com.api_contact_manager.models.Contact;
import com.api_contact_manager.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact addContact(Contact newContact) {
        return contactRepository.save(newContact);
    }
}
