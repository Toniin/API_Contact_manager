package com.api_contact_manager.services;

import com.api_contact_manager.models.Contact;
import com.api_contact_manager.repositories.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactService {
    private ContactRepository contactRepository;

    public void addContact(Contact newContact) {
        contactRepository.save(newContact);
    }

    public Iterable<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(String phoneNumber) {
        return contactRepository.findById(phoneNumber).get();
    }

    public void deleteContact(String phoneNumber) {
        Contact contactFound = contactRepository.findById(phoneNumber).get();

        contactRepository.delete(contactFound);
    }

    public void updateContact(String phoneNumber, Contact updateContact) {
        Contact contactFound = contactRepository.findById(phoneNumber).get();
        contactFound.setName(updateContact.getName());

        contactRepository.save(contactFound);
    }
}
