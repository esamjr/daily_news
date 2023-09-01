package com.agl.daily_news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agl.daily_news.helper.ResponseHandler;
import com.agl.daily_news.model.PasswordReset;
import com.agl.daily_news.model.User;
import com.agl.daily_news.service.User.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHandler.responseError(HttpStatus.BAD_REQUEST.value(), "Validation error", bindingResult.getAllErrors());
        }
        try {
            User registeredUser = userService.register(userDto);
            return ResponseHandler.responseData(HttpStatus.OK.value(), "User registered successfully", registeredUser);
        } catch (Exception e) {
            return ResponseHandler.responseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Validated @RequestBody User userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            
            return ResponseHandler.responseError(HttpStatus.BAD_REQUEST.value(), "Validation error", bindingResult.getAllErrors());
        }
        try {
            User loggedInUser = userService.login(userDto);

            return ResponseHandler.responseData(HttpStatus.OK.value(), "Login successful", loggedInUser);
        } catch (Exception e) {
            return ResponseHandler.responseError(HttpStatus.BAD_REQUEST.value(), "Login failed", e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Validated @RequestBody PasswordReset resetPasswordDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHandler.responseError(HttpStatus.BAD_REQUEST.value(), "Validation error", bindingResult.getAllErrors());
        }
        try {
            userService.resetPassword(resetPasswordDto, null);
            return ResponseHandler.responseMessage(HttpStatus.OK.value(), "Password reset successful", true);
        } catch (Exception e) {
            return ResponseHandler.responseError(HttpStatus.BAD_REQUEST.value(), "Password reset failed", e.getMessage());
        }
    }
}

