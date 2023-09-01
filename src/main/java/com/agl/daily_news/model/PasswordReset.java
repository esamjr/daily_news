package com.agl.daily_news.model;

import jakarta.validation.constraints.NotBlank;

public class PasswordReset {
    @NotBlank(message = "New password cannot be empty")
    private String newPassword;
}
