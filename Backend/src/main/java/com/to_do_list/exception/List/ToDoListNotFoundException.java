package com.to_do_list.exception.List;

public class ToDoListNotFoundException extends RuntimeException {

    public ToDoListNotFoundException(String message) {
        super(message);
    }
}
