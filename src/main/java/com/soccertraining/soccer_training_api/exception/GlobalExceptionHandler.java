package com.soccertraining.soccer_training_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //exception para cuando es duplicado
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<HttpGlobalResponse<Object>> handleDuplicate(
            DuplicateResourceException ex) {

        HttpGlobalResponse<Object> response =
                new HttpGlobalResponse<>();

        response.setData(null);
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    //exception para cuando no se encuntra
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpGlobalResponse<Object>> handleNotFound(ResourceNotFoundException ex) {

        HttpGlobalResponse<Object> response = new HttpGlobalResponse<>();
        response.setData(null);
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //Execpcion para la validacion de los 3 entremamientos
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<HttpGlobalResponse<Object>>
    handleBusiness(BusinessException ex) {

        HttpGlobalResponse<Object> response =
                new HttpGlobalResponse<>();

        response.setData(null);
        response.setMessage(ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpGlobalResponse<Object>>
    handleValidation(MethodArgumentNotValidException ex) {

        String error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse("Error de validación");

        HttpGlobalResponse<Object> response =
                new HttpGlobalResponse<>();

        response.setData(null);
        response.setMessage(error);

        return ResponseEntity
                .badRequest()
                .body(response);
    }


}
