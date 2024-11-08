package com.to_do_list.exception;

import com.to_do_list.exception.List.CreateListIllegalArgumentException;
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

}
