package com.soccertraining.soccer_training_api.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException (String message){
        super(message);
    }
}
