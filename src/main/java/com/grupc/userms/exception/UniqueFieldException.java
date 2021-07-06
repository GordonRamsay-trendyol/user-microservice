package com.grupc.userms.exception;

import org.springframework.http.HttpStatus;

public class UniqueFieldException extends UserException {
    public UniqueFieldException() {
        super(HttpStatus.BAD_REQUEST, "Email is associated with an existing account");
    }

    public UniqueFieldException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
