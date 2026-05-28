package org.example.controller;

import org.example.model.Job;
import org.example.model.User;
import org.example.service.JobServiceImpl;
import org.example.service.UserServiceImpl;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/user")
    public List<User> getUserByName (@RequestBody String name){
        return null;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        return null;
    }

    @PostMapping("/add")
    // Method to add/create a new user
    // Send the user object to the service layer to create/save the user
    public User addUser(@RequestBody User user) {
        return userService.createNewUser(user);
    }

    @PostMapping("/login")
    // Method used to log a user in
    // Send the user's login details to the service layer
    public String login(@RequestBody User user){
        return userService.login(user);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        //YOUR CODE STARTS HERE

        return null;

        //YOUR CODE ENDS HERE
    }

    @PutMapping("/updatePass/{id}")
    public ResponseEntity updatePass(@PathVariable int id, @RequestBody String password,String user,
                           @RequestHeader("Authorization") String authHeader
    ) {
        //YOUR CODE STARTS HERE
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid token");
        }

        // Extract JWT token
        String token = authHeader.substring(7);

        try {

            // Extract username from token
            String username = jwtUtil.extractUsername(token);

            // Optional: ensure user can only change their own password
            if (!user.equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access denied");
            }

            return ResponseEntity.ok("Success");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

        //YOUR CODE ENDS HERE
    }

    @PutMapping("/addSkills/{id}")
    public User addSkills(@PathVariable int id, @RequestBody List<String> Skills) {
        //YOUR CODE STARTS HERE

        return null;

        //YOUR CODE ENDS HERE
    }

    @PutMapping("/updatestatus/{id}")
    public User updateStatus(@PathVariable int id, @RequestBody String status) {
        //YOUR CODE STARTS HERE

        return null;

        //YOUR CODE ENDS HERE
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        //YOUR CODE STARTS HERE


        //YOUR CODE ENDS HERE
    }

    @DeleteMapping("/{id}")
    public void deleteUserJob(@PathVariable Job job) {
        //YOUR CODE STARTS HERE


        //YOUR CODE ENDS HERE
    }





}
