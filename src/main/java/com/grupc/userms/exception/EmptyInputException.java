package com.grupc.userms.exception;

import org.springframework.http.HttpStatus;

public class EmptyInputException extends UserException {
    public EmptyInputException() {
        super(HttpStatus.BAD_REQUEST, "Please Enter Valid Input");
    }

    public EmptyInputException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
