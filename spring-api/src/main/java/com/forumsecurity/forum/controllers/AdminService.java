package com.forumsecurity.forum.controllers;

import com.forumsecurity.forum.models.User;
import com.forumsecurity.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Dohvati sve korisnike
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Dodaj ili ažuriraj korisnika
    public User createUser(User user) {
        if (user.getId() != null) {
            // If the user ID is not null, check if the user already exists in the database
            Optional<User> existingUser = userRepository.findById(user.getId());

            if (existingUser.isPresent()) {
                // Keep the original password if the user already exists
                user.setPassword(existingUser.get().getPassword());
            }
        } else {
            // If the user is new (ID is null), encode the password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }



    // Obriši korisnika po ID-u
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // Pronađi korisnika po korisničkom imenu
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
