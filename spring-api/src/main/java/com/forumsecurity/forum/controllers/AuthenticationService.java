package com.forumsecurity.forum.controllers;

import com.forumsecurity.forum.models.User;
import com.forumsecurity.forum.repositories.UserRepository;
import com.forumsecurity.forum.services.JWTService;
import com.forumsecurity.forum.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;
    @Autowired
    private JWTService jwtService;

    public Map<String, Object> authenticate(User user) {
        // Pronađi korisnika po korisničkom imenu
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());

        // Proveri da li korisnik postoji i da li se lozinka poklapa
        if (optionalUser.isPresent() && passwordEncoder.matches(user.getPassword(), optionalUser.get().getPassword())) {
            User authenticatedUser = optionalUser.get();
            String token = jwtService.generateToken(authenticatedUser);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", authenticatedUser);


            //send mail
            String verificationCode = mailService.generateVerificationCode();
            mailService.saveVerificationCode(authenticatedUser.getUsername(),verificationCode);
            mailService.sendVerificationCode(authenticatedUser.getEmail(),verificationCode);




            return response;
        } else {
            return null;
        }
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
