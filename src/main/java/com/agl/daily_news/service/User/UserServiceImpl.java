package com.agl.daily_news.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agl.daily_news.exception.CustomException;
import com.agl.daily_news.model.PasswordReset;
import com.agl.daily_news.model.User;
import com.agl.daily_news.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserRole(3);

        // Save the user to the database
        return userRepository.save(user);
    }

    @Override
    public User login(User userDto) throws CustomException {
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null || !passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid username or password");
        }
        return user;
    }

    @Override
    public void resetPassword(PasswordReset resetPasswordDto, User userDto) throws CustomException {
        // Implement password reset logic based on your requirements
        // For example, retrieve the user by email, generate a new password, and update the password in the database

        User user = userRepository.findByEmail(userDto.getEmail());
        return;

    }
}
