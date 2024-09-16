package com.api_contact_manager.services;

import com.api_contact_manager.configuration.WebConfig;
import com.api_contact_manager.models.User;
import com.api_contact_manager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private WebConfig webConfig;

    public boolean isUserExists(String username) {
        if (userRepository.findByUsername(username) != null) {
            return true;
        } else {
            return false;
        }
    }

    public User createUser(User user) {
        user.setPassword(webConfig.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User : " + username + " not found");
        }

        return user;
    }
}
