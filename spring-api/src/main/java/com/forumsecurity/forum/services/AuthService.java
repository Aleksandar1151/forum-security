package com.forumsecurity.forum.services;

/*
import com.forumsecurity.forum.models.User;
import com.forumsecurity.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(String username, String password) {
        // Pronađi korisnika po korisničkom imenu
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // Proveri da li korisnik postoji i da li se lozinka poklapa
        if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            return optionalUser.get();
        }
        return null;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
*/