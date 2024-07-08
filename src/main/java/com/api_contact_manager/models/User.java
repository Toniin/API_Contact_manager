package com.api_contact_manager.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data /* Annotation to auto-generate getters/setters */
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name="role")
    private String role;
}
