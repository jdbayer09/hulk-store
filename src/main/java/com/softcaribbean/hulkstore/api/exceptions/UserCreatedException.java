package com.softcaribbean.hulkstore.api.exceptions;


public class UserCreatedException extends RuntimeException {
    public UserCreatedException(String message) {
        super(message);
    }

    public UserCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
