package org.example.controller;

import org.example.model.*;
import org.example.service.PreferenceServiceInterface;
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

    @Autowired
    private PreferenceServiceInterface preferenceService;

    @GetMapping("/user")
    public ResponseEntity getUserByEmail (@RequestBody String email){
        User user =userService.findUserByEmail(email);
        if(user ==null ){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not matching");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable int id){
        User user =userService.findUserById(id);
        if(user ==null ){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not matching");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add")
    // Method to add/create a new user
    // Send the user object to the service layer to create/save the user
    public ResponseEntity addUser(@RequestBody User user) {
        User newUser =userService.createNewUser(user);
        if(newUser ==null ){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not matching");
        }
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    // Method used to log a user in
    // Send the user's login details to the service layer
    public ResponseEntity<String> login(@RequestBody User user){
        String jwt = userService.login(user);
        if(jwt ==null ){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not matching");
        }
        return ResponseEntity.ok(jwt);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable int id, @RequestBody User user
            ,
                           @RequestHeader("Authorization") String authHeader) {
        //YOUR CODE STARTS HERE

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid token");
        }

        String token = authHeader.substring(7);

        try {

            String username = jwtUtil.extractUsername(token);


            // Optional: ensure user can only change their own password
            if (!user.equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access denied");
            }
            User newUser =userService.editUser(user);
            if(newUser ==null ){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not matching");
            }

            return ResponseEntity.ok(newUser);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

        //YOUR CODE ENDS HERE
    }

    @PutMapping("/updatePass/{id}")
    public ResponseEntity updatePass(@PathVariable int id,
                                     @RequestBody String password,
                                     @RequestParam String user,
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
            userService.editPassword(password, id); // hashed in the service
            return ResponseEntity.ok("Success");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

        //YOUR CODE ENDS HERE
    }

    @PostMapping("/addstatus/{id}")
    public ResponseEntity addStatus(@PathVariable int id, @RequestBody TrackedJob status,
                             @RequestHeader("Authorization") String authHeader) {
        //YOUR CODE STARTS HERE

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid token");
        }

        try {

            return ResponseEntity.ok(userService.addJobstatus(status));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }


        //YOUR CODE ENDS HERE
    }

    @PutMapping("/updatestatus/{id}")
    public ResponseEntity updateStatus(@PathVariable int id, @RequestBody TrackedJob status,
                             @RequestHeader("Authorization") String authHeader) {
        //YOUR CODE STARTS HERE

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid token");
        }

        try {

            return ResponseEntity.ok(userService.updateJobstatus(id,status));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }


        //YOUR CODE ENDS HERE
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id,
                                             @RequestHeader("Authorization") String authHeader) {
        //YOUR CODE STARTS HERE

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid token");
        }

        // Extract JWT token
        String token = authHeader.substring(7);

        try {
            User user = userService.findUserById(id);
            // Extract username from token
            String username = jwtUtil.extractUsername(token);

            // Optional: ensure user can only change their own password
            if(user != null){
                if (!user.getEmail_address().equals(username) ) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Access denied");
                }
                userService.deleteUser(id);
            }



            return ResponseEntity.ok("Success");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }
        //YOUR CODE ENDS HERE
    }

    @GetMapping("/Userjob/{id}")
    public ResponseEntity getUserjob(
            @RequestBody Long userId,
            @RequestHeader("Authorization") String authHeader)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid token");
        }

        try {


            return ResponseEntity.ok(userService.getUserJobs(userId));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

    }

    @DeleteMapping("/{id}/job")
    public void deleteUserJob(@PathVariable int id, @RequestBody Job job) {
        //YOUR CODE STARTS HERE


        //YOUR CODE ENDS HERE
    }


    // GET /user/profile — current user's info + preferences (from the JWT). For the profile page.
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }
        try {
            String email = jwtUtil.extractUsername(authHeader.substring(7));
            User user = userService.findUserByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
            UserProfile profile = new UserProfile();
            profile.setId(user.getId());
            profile.setFirst_name(user.getFirst_name());
            profile.setLast_name(user.getLast_name());
            profile.setEmail_address(user.getEmail_address());
            profile.setPreferences(preferenceService.getPreferencesByUserId(user.getId()));
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    // PUT /user/profile — update the authenticated user's name (email is not editable).
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody User body,
                                           @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }
        try {
            String email = jwtUtil.extractUsername(authHeader.substring(7));
            User user = userService.findUserByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
            if (body.getFirst_name() == null || body.getFirst_name().isBlank()
                    || body.getLast_name() == null || body.getLast_name().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First and last name are required");
            }
            String first = body.getFirst_name().trim();
            String last = body.getLast_name().trim();
            userService.updateName(first, last, user.getId());

            UserProfile profile = new UserProfile();
            profile.setId(user.getId());
            profile.setFirst_name(first);
            profile.setLast_name(last);
            profile.setEmail_address(user.getEmail_address());
            profile.setPreferences(preferenceService.getPreferencesByUserId(user.getId()));
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }



}
