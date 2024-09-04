package com.api_contact_manager.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Document(collection = "users")
@Data /* Annotation to auto-generate getters/setters */
public class User implements UserDetails {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String password;
    @Indexed(unique = true)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
}
