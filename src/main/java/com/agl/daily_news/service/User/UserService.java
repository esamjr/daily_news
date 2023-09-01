package com.agl.daily_news.service.User;

import com.agl.daily_news.exception.CustomException;
import com.agl.daily_news.model.PasswordReset;
import com.agl.daily_news.model.User;

public interface UserService {
    User register(User userDto) throws CustomException;
    User login(User userDto) throws CustomException;
    void resetPassword(PasswordReset resetPasswordDto, User userDto) throws CustomException;

}
