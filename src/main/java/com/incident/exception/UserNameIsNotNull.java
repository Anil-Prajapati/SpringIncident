package com.incident.exception;

public class UserNameIsNotNull extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNameIsNotNull(String message) {
        super(message);
    }

    public UserNameIsNotNull(String message, Throwable throwable) {
        super(message, throwable);
    }
}
