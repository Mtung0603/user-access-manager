package com.r2s.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {


    @NotBlank(message = "Username khong duoc bo trong")
    private String username ;
    @NotBlank(message = "Password khong duoc bo torng ")
    private String password ;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
