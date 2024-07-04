package com.api_contact_manager.services;

import com.api_contact_manager.models.Contact;
import com.api_contact_manager.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public void addContact(Contact newContact) {
        contactRepository.save(newContact);
    }

    public Iterable<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long phoneNumber) {
        return contactRepository.findById(phoneNumber);
    }
}
