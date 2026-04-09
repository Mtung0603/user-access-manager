package com.r2s.auth.dto;

import com.r2s.auth.entity.Role;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {


    @NotBlank(message = "User name khong duoc bo trong")
    private String username;

    @NotBlank(message = "Password khong duoc de trong")
    private String password;
    private Role role;
    private String email ;
    private String fullname ;

    public RegisterRequest() {
    }


    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
