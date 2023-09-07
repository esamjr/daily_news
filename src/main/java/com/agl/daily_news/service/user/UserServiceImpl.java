package com.agl.daily_news.service.user;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.agl.daily_news.config.JwtUtil;
import com.agl.daily_news.exception.CustomException;
import com.agl.daily_news.helper.ResponseHandler;
import com.agl.daily_news.model.User;
import com.agl.daily_news.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;

    
    @Autowired    
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User userDto) throws CustomException {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new CustomException("Username is already taken");
        }
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new CustomException("Email is already registered");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setUserRole(userDto.getUserRole());
        return userRepository.save(user);
    }

    // @Override
    // public User login(User userDto) throws CustomException {
    //     User user = userRepository.findByUsername(userDto.getUsername());
    //     if (user == null || !userDto.getPassword().equals(user.getPassword())) {
    //         throw new CustomException("Invalid username or password");
    //     }
    //     return user;
    // }
    @Override
    public ResponseEntity<?> login(User request) {
        try {
            // Create a UsernamePasswordAuthenticationToken for authentication
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

            // Authenticate the user using the AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            // Set the authenticated user in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate a JWT token
            String token = jwtUtil.createToken(request.getEmail());

            Map<String, Object> data = new HashMap<>();
            data.put("email", request.getEmail());
            data.put("token", token);

            // Return a success response with the JWT token
            return ResponseEntity.status(HttpStatus.OK)
                .body(data);
        } catch (Exception e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Authentication failed", "error", e.getMessage()));
        }
    }


    @Override
    public void updatePassword(String email, String newPassword) throws CustomException {
        User user = userRepository.findByEmail(email);
        if (user == null && newPassword == null) {
            throw new CustomException("User not found with the provided email address OR New Password cant be null");
        } else {
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }
}
