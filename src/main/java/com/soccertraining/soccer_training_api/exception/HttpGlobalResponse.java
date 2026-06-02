package com.soccertraining.soccer_training_api.exception;

import lombok.Data;

@Data
public class HttpGlobalResponse<T> {
    private T data;
    private String message;

}
