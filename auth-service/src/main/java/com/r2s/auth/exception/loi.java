package com.r2s.auth.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class loi {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernamNotFound(UsernameNotFoundException e){
        System.out.println("ashdusad");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()) ;
    }

    @ExceptionHandler public ResponseEntity<String> handleRuntimeException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()) ;
    }
}
