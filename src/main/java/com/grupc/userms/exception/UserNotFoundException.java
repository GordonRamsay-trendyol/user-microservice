package com.grupc.userms.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException{
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND,"User Not Found" );
    }

    public UserNotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
