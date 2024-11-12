package com.to_do_list.exception.user;

public class WrongCredentialException extends RuntimeException {

    public WrongCredentialException(String message) {
        super(message);
    }
}
