package com.ajay.projectmanagementsystem.controller;

import com.ajay.projectmanagementsystem.model.User;
import com.ajay.projectmanagementsystem.repo.UserRepository;
import com.ajay.projectmanagementsystem.service.CustomUserDetailsImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<User>createUserHandler(@RequestBody User user) throws Exception {

        User isUserExist=userRepository.findByEmail(user.getEmail());

        if(isUserExist!=null){
            throw new Exception("email already exist with another account");
        }

        User createUser=new User();
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setEmail(user.getEmail());
        createUser.setName(user.getName());

        User savedUser = userRepository.save(createUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


}
