package com.easytrack.controller;

import com.easytrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.easytrack.model.User;

import javax.validation.Valid;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public User getUser(@Valid @RequestBody User user){
        return userRepository.findByEmail(user.getEmail());
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user){
        if(userRepository.findByEmail(user.getEmail()) == null) {
            return userRepository.save(user);
        } else {
            return new User();
        }
    }

    @GetMapping("/user/id")
    public Long getUserId(@Valid @RequestBody User user){ return userRepository.findByEmail(user.getEmail()).getId();}
}
