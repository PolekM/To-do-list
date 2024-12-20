package com.to_do_list.exception;

import com.to_do_list.exception.List.CreateListIllegalArgumentException;
import com.to_do_list.exception.List.ToDoListNotFoundException;
import com.to_do_list.exception.Task.TaskNotFoundException;
import com.to_do_list.exception.user.EmailIsBusyException;
import com.to_do_list.exception.user.WrongCredentialException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {

    public ResponseEntity<Error> responseEntityExceptionBuilder(HttpStatus httpStatus, String massage) {
        Error error = new Error();

        error.setCode(httpStatus.value());
        error.setMessage(massage);
        error.setErrorTime(new Date());

        return new ResponseEntity<>(error, httpStatus);

    }
    @ExceptionHandler({CreateListIllegalArgumentException.class})
    public ResponseEntity<Error> handleBadRequestException(Exception e) {
        return responseEntityExceptionBuilder(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({ToDoListNotFoundException.class, TaskNotFoundException.class})
    public ResponseEntity<Error> handlerNotFoundException(Exception e) {
        return responseEntityExceptionBuilder(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({WrongCredentialException.class})
    public ResponseEntity<Error> handleUnauthorizedException(Exception exception) {
        return responseEntityExceptionBuilder(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler({EmailIsBusyException.class})
    public ResponseEntity<Error> handleDuplicateException(Exception exception) {
        return responseEntityExceptionBuilder(HttpStatus.CONFLICT, exception.getMessage());
    }

}
