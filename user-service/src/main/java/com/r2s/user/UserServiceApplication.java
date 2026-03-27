package com.r2s.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



 @SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.r2s.auth.AuthServiceApplication.class,args) ;
    }
}

