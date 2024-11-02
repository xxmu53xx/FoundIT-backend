package com.g4AppDev.FoundIT.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

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

    @DeleteMapping("/deleteUserDetails/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
    
    
   //first 5 new users added (pde ni maalter para mahimo og first 3)
    @GetMapping("/getLatestUsers")
    public List<UserEntity> getLatestUsers(@RequestParam(defaultValue = "5") int count) {
        return userService.getLatestUsers(count);
    }
     //para ni sa counting kun pila ka users naa sa list
    @GetMapping("/getCountUsers")
    public ResponseEntity<Map<String, Long>> getEntityCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("user_count", userService.getUserCount());
        return ResponseEntity.ok(counts);
    }

}
