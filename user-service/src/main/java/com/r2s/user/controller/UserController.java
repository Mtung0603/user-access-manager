package com.r2s.user.controller;


import com.r2s.user.Service.UserService;

import com.r2s.user.dto.UpdateUserRequest;
import com.r2s.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

     private final  UserService userService ;

    public UserController(UserService svc) {
        this.userService = svc;
    }



     @GetMapping
     @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<List<UserResponse>>getAllUsers(){
            return  ResponseEntity.ok(userService.getAllUsers()) ;

     }

     @GetMapping("/me")
    public ResponseEntity<UserResponse> getmyProfile(Authentication authentication){
        String username  =authentication.getName() ;
        return ResponseEntity.ok(userService.getUserByUserName(username)) ;

     }
     @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMyProfile( @RequestBody
    UpdateUserRequest request ,Authentication authentication){
        String username = authentication.getName() ;
        return ResponseEntity.ok(userService.updateUser(username,request)) ;


    }



    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String username){
       try{
           userService.deleteUser(username);
           return  ResponseEntity.noContent().build();
       }catch (UsernameNotFoundException e){
           return ResponseEntity.notFound().build();
       }
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> UpdateByAdmin(
            @PathVariable String  username ,
            @RequestBody UpdateUserRequest request){
        try{
            return ResponseEntity.ok(userService.updateUser(username,request));
        } catch (UsernameNotFoundException e) {
             return ResponseEntity.notFound().build() ;
        }
    }

}
