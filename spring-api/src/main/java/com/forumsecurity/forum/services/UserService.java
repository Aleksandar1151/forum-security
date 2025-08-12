package com.forumsecurity.forum.services;

import com.forumsecurity.forum.models.User;
import com.forumsecurity.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Pronađi korisnika po korisničkom imenu
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Pronađi sve korisnike
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Sačuvaj korisnika (za registraciju ili ažuriranje)
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Obrisi korisnika po ID-u
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}