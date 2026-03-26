package Service;

import com.r2s.auth.entity.User;
import com.r2s.auth.repository.UserRepository;
import controller.UserController;
import dto.UpdateUserRequest;
import dto.UserResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

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
        repo.deleteByUsername(username);
   }

}
