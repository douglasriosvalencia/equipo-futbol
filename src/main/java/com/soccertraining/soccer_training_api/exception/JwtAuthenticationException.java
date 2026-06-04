package com.soccertraining.soccer_training_api.exception;

public class JwtAuthenticationException extends RuntimeException{
    public JwtAuthenticationException(String message){
        super(message);
    }
}
