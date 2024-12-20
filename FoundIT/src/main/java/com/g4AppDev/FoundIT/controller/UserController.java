package com.g4AppDev.FoundIT.controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Inject UserRepo properly
    @Autowired
    private UserRepo userRepository;
    
    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user); // Return the user if found
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if not found
        }
    }
    
    @GetMapping("/getUserByEmail/{email}") // Corrected mapping
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email) { // Corrected method name
        UserEntity user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if user not found
        }
    }
    @GetMapping("/getAllUsers")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/postUsers")
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @PutMapping("/putUserDetails/{id}")
    public UserEntity putUserDetails(@PathVariable Long id, @RequestBody UserEntity userDetails) {
        return userService.updateUser(id, userDetails);
    }
    @GetMapping("/getLeaderboard")
    public List<UserEntity> getLeaderboard() {
        return userService.getTopUsersByPoints();
    }
    @DeleteMapping("/deleteUserDetails/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @GetMapping("/getLatestUsers")
    public List<UserEntity> getLatestUsers(@RequestParam(defaultValue = "5") int count) {
        return userService.getLatestUsers(count);
    }

    @GetMapping("/getCountUsers")
    public ResponseEntity<Map<String, Long>> getEntityCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("user_count", userService.getUserCount());
        return ResponseEntity.ok(counts);
    }

    /*
   //MasBetter ni siya pero dli mo generate og token for some reason
    @GetMapping("/getCurrentUser")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            System.out.println("Authenticated username: " + username);  // Log the username
            UserEntity user = userRepository.findBySchoolEmail(username);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if user is not found
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }*/
    
}