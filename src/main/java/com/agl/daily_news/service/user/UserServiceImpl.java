package com.agl.daily_news.service.user;

import org.springframework.stereotype.Service;

import com.agl.daily_news.exception.CustomException;
import com.agl.daily_news.model.User;
import com.agl.daily_news.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
        user.setPassword(userDto.getPassword());
        user.setUserRole(userDto.getUserRole());

        return userRepository.save(user);
    }

    @Override
    public User login(User userDto) throws CustomException {
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null || !userDto.getPassword().equals(user.getPassword())) {
            throw new CustomException("Invalid username or password");
        }
        return user;
    }

    @Override
    public void updatePassword(String email, String newPassword) throws CustomException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("User not found with the provided email address");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
