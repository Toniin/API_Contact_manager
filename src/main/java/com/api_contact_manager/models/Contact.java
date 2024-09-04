package com.api_contact_manager.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contacts")
@Data /* Annotation to auto-generate getters/setters */
public class Contact {

    @Id
    private String phoneNumber;

    @Indexed(unique = true)
    private String name;
}
