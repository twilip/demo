package com.example.controller;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.payload.JWTAuthResponse;
import com.example.payload.LoginDto;
import com.example.payload.SignInDto;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;


    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }
    @PostMapping("/signUp")
    public ResponseEntity<String> signupUser(@RequestBody SignInDto signInDto){
        if (userRepository.existsByUsername(signInDto.getUsername())){
            return new ResponseEntity<>("already taken",HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signInDto.getEmail())){
            return new ResponseEntity<>("already taken",HttpStatus.BAD_REQUEST);

        }


        User user=new User();
        user.setName(signInDto.getName());
        user.setEmail(signInDto.getEmail());
        user.setUsername(signInDto.getUsername());
        user.setPassword(passwordEncoder.encode(signInDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return new ResponseEntity<>("user sign in successfull", HttpStatus.OK);
    }


}

