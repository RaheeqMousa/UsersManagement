package com.example.UsersManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final SlackNotifier slackNotifier;

    public GlobalExceptionHandler(SlackNotifier notifier){
        this.slackNotifier=notifier;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex){
        String exceptionMessage= "Exception from Raheeq's app: "+ex.getMessage();

        slackNotifier.send(exceptionMessage);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionMessage);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExist(UserAlreadyExistException ex){
        String exceptionMessage= "Exception from Raheeq's app: "+ex.getMessage();

        slackNotifier.send(exceptionMessage);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionMessage);
    }

}
