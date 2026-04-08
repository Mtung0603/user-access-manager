package com.r2s.user.Service;

import com.r2s.user.Entity.User;
import com.r2s.user.repository.UserRepository;
import com.r2s.user.dto.UpdateUserRequest;
import com.r2s.user.dto.UserResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserService {

    private final UserRepository repo ;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<UserResponse> getAllUsers(){
        return repo.findAll().stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList()) ;
    }

   public UserResponse getUserByUserName(String username){
        return  repo.findByUsername(username)
                .map(UserResponse::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
   }
   public UserResponse updateUser(String username , UpdateUserRequest req){
        User user =repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        return UserResponse.fromEntity(repo.save(user)) ;
   }

   public void deleteUser(String username){
           if (!repo.existsByUsername(username)){
               throw new UsernameNotFoundException("khong tim thay " + username);
           }
           repo.deleteByUsername(username);

   }

}
