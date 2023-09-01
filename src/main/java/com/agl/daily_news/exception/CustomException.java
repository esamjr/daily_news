package com.agl.daily_news.exception;

public class CustomException extends Exception {

    public CustomException(String message) {
        super(message);
    }

    // Constructor that takes a custom error message and a cause
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}

