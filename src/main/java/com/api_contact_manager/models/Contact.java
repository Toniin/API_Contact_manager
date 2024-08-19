package com.api_contact_manager.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "contact")
@Data /* Annotation to auto-generate getters/setters */
public class Contact {

    @Id
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="name")
    private String name;
}
