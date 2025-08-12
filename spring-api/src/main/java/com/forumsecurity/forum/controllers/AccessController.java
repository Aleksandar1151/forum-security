package com.forumsecurity.forum.controllers;

import com.forumsecurity.forum.models.Comment;
import com.forumsecurity.forum.models.Forum;
import com.forumsecurity.forum.models.MailStructure;
import com.forumsecurity.forum.models.User;
import com.forumsecurity.forum.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/forums")
public class AccessController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AdminService adminService;

    @Autowired
    //private JWTController jwtController;
    JWTService jwtService;

    @Autowired
    //private JWTController jwtController;
    LoggingService loggingService;

    // Prijava korisnika kroz AccessController
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        loggingService.log(user.toString());
        Map<String, Object> response = authenticationService.authenticate(user);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // Validacija JWT tokena kroz AccessController
    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        loggingService.log(request.toString());
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;

        // Check if the Authorization header contains the Bearer token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        // Validate the token
        if (token != null && jwtService.validateToken(token)) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }


    // Dohvati sve korisnike kroz AccessController (samo za administratore)
    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    // Kreiraj ili ažuriraj korisnika kroz AccessController
    @PostMapping("/admin/users")
    public User createUser(@RequestBody User user) {
        loggingService.log(user.toString());
        return adminService.createUser(user);
    }

    // Obriši korisnika kroz AccessController
    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        loggingService.log(userId.toString());
        adminService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    // Pronađi korisnika po korisničkom imenu kroz AccessController
    @GetMapping("/admin/users/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        loggingService.log(username.toString());
        Optional<User> user = adminService.getUserByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint za dobijanje svih foruma
    @GetMapping
    public List<Forum> getAllForums() {
        return forumService.getAllForums();
    }

    // Endpoint za dobijanje jednog foruma po ID-u
    @GetMapping("/{id}")
    public ResponseEntity<Forum> getForumById(@PathVariable Long id) {
        Forum forum = forumService.getForumById(id);
        if (forum != null) {
            return ResponseEntity.ok(forum);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint za kreiranje novog foruma
    @PostMapping
    public Forum createForum(@RequestBody Forum forum) {
        return forumService.createForum(forum);
    }

    // Endpoint za dodavanje novog komentara u forum
    @PostMapping("/{forumId}/comments")
    public ResponseEntity<Comment> addCommentToForum(@PathVariable Long forumId, @RequestBody Comment comment) {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        comment.setCreatedTime(myDateObj.format(myFormatObj));



        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            return ResponseEntity.status(404).body(null); // Return 404 if the forum is not found
        }

        // Set the forum to the comment
        comment.setForum(forum);

        // Save the comment
        Comment savedComment = commentService.addCommentToForum(forumId, comment);

        return ResponseEntity.ok(savedComment);
    }

    // Endpoint za dobijanje svih komentara u određenom forumu
    @GetMapping("/{forumId}/comments")
    public List<Comment> getCommentsByForumId(@PathVariable Long forumId) {
        return commentService.getCommentsByForumId(forumId);
    }

    @PutMapping("/{forumId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long forumId, @PathVariable Long commentId, @RequestBody Comment updatedComment) {
        Comment existingComment = commentService.getCommentById(commentId);

        System.out.println("forumId"+ forumId);
        System.out.println("commentId"+ commentId);

        System.out.println("updatedComment"+ updatedComment);
        System.out.println("existingComment"+existingComment);

        if (existingComment == null || !existingComment.getForum().getId().equals(forumId)) {
            return ResponseEntity.status(404).body(null); // Return 404 if comment or forum not found
        }


        existingComment.setContent(updatedComment.getContent());
        existingComment.setCreatedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        existingComment.setAllowed((updatedComment.getAllowed()));

        Comment savedComment = commentService.updateComment(existingComment);
        return ResponseEntity.ok(savedComment);
    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        Comment existingComment = commentService.getCommentById(commentId);

        if (existingComment == null) {
            return ResponseEntity.status(404).body("Comment not found");
        }

        commentService.deleteCommentById(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }


    //MAILS


    @Autowired
    private MailService mailService;
    @PostMapping("/sendmail/{mail}")
    public String sendMail(@PathVariable String mail) {
        MailStructure mailStructure = new MailStructure("Account creation", "Your account was successfully created.");
        mailService.sendWelcomeMail(mail,mailStructure);
        return "Successfully sent the mail!";
    }


    @PostMapping("/verify-code/{username}/{code}")
    public ResponseEntity<String> verifyCode(@PathVariable String username, @PathVariable String code) {
        // Verify the code entered by the user
        boolean isValid = mailService.verifyCode(username, code);

        if (isValid) {
            mailService.removeCode(username); // Clean up code after successful verification
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.ok("Login failed.");
        }
    }






}
