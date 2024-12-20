package com.to_do_list.exception.user;

public class EmailIsBusyException extends RuntimeException{

    public EmailIsBusyException(String message) {
        super(message);
    }
}
