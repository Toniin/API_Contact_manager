package com.api_contact_manager.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data /* Annotation to auto-generate getters/setters */
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @Column(name="phone_number")
    private int phoneNumber;
    @Column(name="name")
    private String name;
}
