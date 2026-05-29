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
    public ResponseEntity<?> getUserByName (@RequestBody String name){
        User user = userService.findUserByUsername(name);
        if (user == null ) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No content");
        }
        return ResponseEntity.ok(user);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        User user = userService.findUserById(id);
        if (user == null ) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No content");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add")
    // Method to add/create a new user
    // Send the user object to the service layer to create/save the user
    public ResponseEntity<?> addUser(@RequestBody User user) {
        User newuser = userService.createNewUser(user);
        if (newuser == null ) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Add failed");
        }
        return ResponseEntity.ok(newuser);
    }

    @PostMapping("/login")
    // Method used to log a user in
    // Send the user's login details to the service layer
    public ResponseEntity<String> login(@RequestBody User user){
        String jwt = userService.login(user);
        if (jwt.equals("Error") ) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Add failed");
        }
        return ResponseEntity.ok(jwt);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user,
        @RequestHeader("Authorization") String authHeader) {
        //YOUR CODE STARTS HERE
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid token");
        }
        String token = authHeader.substring(7);

        try {

            // Extract username from token
            String username = jwtUtil.extractUsername(token);

            // Optional: ensure user can only change their own password
            if (!user.equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access denied");
            }
            userService.editUser(user);
            return ResponseEntity.ok(user);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

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

            userService.editPassword(password, id);

            return ResponseEntity.ok("Success");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

        //YOUR CODE ENDS HERE
    }

    @PutMapping("/addSkills/{id}")
    public ResponseEntity<?> addSkills(@PathVariable int id, @RequestBody List<String> Skills,
                          @RequestHeader("Authorization") String authHeader) {
        //YOUR CODE STARTS HERE
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid token");
        }
        String token = authHeader.substring(7);

        try {


            User user = userService.addSkills(Skills, id);

            return ResponseEntity.ok(user);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

    }

    @PutMapping("/updatestatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable int id, @RequestBody String status,
      @RequestHeader("Authorization") String authHeader) {
        //YOUR CODE STARTS HERE
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid token");
        }
        String token = authHeader.substring(7);

        try {


            userService.updateJobstatus(id,status);


            return ResponseEntity.ok("success");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }


        //YOUR CODE ENDS HERE
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        //YOUR CODE STARTS HERE
        userService.deleteUser(id);
        return ResponseEntity.ok("Success");
        //YOUR CODE ENDS HERE
    }

    @DeleteMapping("/{id}/job")
    public void deleteUserJob(@PathVariable int id, @RequestBody Job job) {
        //YOUR CODE STARTS HERE

        //YOUR CODE ENDS HERE
    }





}
