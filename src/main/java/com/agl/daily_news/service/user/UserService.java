package com.agl.daily_news.service.user;

import com.agl.daily_news.exception.CustomException;
import com.agl.daily_news.model.User;

public interface UserService {
    User register(User userDto) throws CustomException;
    User login(User userDto) throws CustomException;
    void updatePassword(String email, String newPassword) throws CustomException;
}
