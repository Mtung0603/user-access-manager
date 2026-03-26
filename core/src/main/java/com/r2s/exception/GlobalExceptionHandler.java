package com.r2s.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustom(CustomException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()) ;


    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handAll (Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error : " +ex.getMessage()) ;
    }

}
