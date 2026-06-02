package org.example.controller;

import org.example.model.User;
import org.example.service.RecruiterServiceImpl;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class RecruiterController {
    @Autowired
    RecruiterServiceImpl recruiterService;

    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/loginrecruiter")
    // Method used to log a user in
    // Send the user's login details to the service layer
    public ResponseEntity<String> loginRecruiter(@RequestBody User user){
        String jwt = recruiterService.login(user);
        if(jwt ==null ){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not matching");
        }
        return ResponseEntity.ok(jwt);
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
            recruiterService.editPassword(password, id); // hashed in the service
            return ResponseEntity.ok("Success");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

        //YOUR CODE ENDS HERE
    }

    @GetMapping("/Userjob/{id}")
    public ResponseEntity getUserjob(
                                     @RequestBody Long jobId,
                                     @RequestHeader("Authorization") String authHeader)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid token");
        }

          try {


            return ResponseEntity.ok(recruiterService.getUserJobs(jobId));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

    }

    @GetMapping("/Companyjob/{id}")
    public ResponseEntity getCompanyjob(
            @RequestBody Long CompanyId,
            @RequestHeader("Authorization") String authHeader)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid token");
        }

        try {


            return ResponseEntity.ok(recruiterService.getUserJobs(CompanyId));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

    }


}
