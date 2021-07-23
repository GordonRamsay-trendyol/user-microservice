package com.grupc.userms.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.grupc.userms.exception.EmptyInputException;
import com.grupc.userms.exception.UniqueFieldException;
import com.grupc.userms.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<String> handleEmptyInput (EmptyInputException emptyInputException){
        return new ResponseEntity<String>("Input is not valid", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException userNotFoundException){
        return new ResponseEntity<String> ("No such user in DB with given ID",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UniqueFieldException.class)
    public ResponseEntity<String> handleNotUniqueField(UniqueFieldException uniqueFieldException){
        return new ResponseEntity<String> ("Email address is already associated with an existing account",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<String> handleBadJsonFormat(JsonParseException jsonParseException){
        return new ResponseEntity<String> ("Please check your Json format",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException httpMessageNotReadableException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No content to map due to end-of-input");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException illegalArgumentException){
        return new ResponseEntity<String> ("No content to map due to end-of-input",HttpStatus.BAD_REQUEST);
    }
}

