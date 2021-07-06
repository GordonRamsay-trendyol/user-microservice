package com.grupc.userms.exception;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public UserException (){
        super();
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        message = "Something happened";
    }

    public UserException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
