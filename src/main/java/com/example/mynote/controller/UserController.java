package com.example.mynote.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mynote.model.Response;
import com.example.mynote.model.User;
import com.example.mynote.repositories.UserRepository;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserRepository userRepo = new UserRepository();

    // @GetMapping("/{id}")
    // public User getUserById(@PathVariable Long id) {
    //     return new User(id, "John Doe", "john.doe@example.com");
    // }

    @PostMapping("/login")
    public Response login(@RequestBody Map<String, String> param) {
        String username = param.get("username");
        String password = param.get("password");
        User user = userRepo.login(username, password);
        if (user != null) {
            return new Response("Login successful", user, 200);
        } else {
            return new Response("Invalid credentials", null, 401);
        }
    }

    @PostMapping("/register")
    public Response register(@RequestBody Map<String, String> param) {
        String username = param.get("username");
        String email = param.get("email");
        String password = param.get("password");
        User user = new User(0, username, email, password);
        boolean success = userRepo.register(user);
        if (success) {
            return new Response("Registration successful", user, 200);
        } else {
            return new Response("Registration failed", null, 400);
        }
    }
    
}
