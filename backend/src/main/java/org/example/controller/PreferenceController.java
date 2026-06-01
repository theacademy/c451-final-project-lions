package org.example.controller;

import org.example.model.User;
import org.example.model.UserPreference;
import org.example.service.PreferenceServiceInterface;
import org.example.service.UserServiceImpl;
import org.example.service.UserServiceInterface;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/preferences")
@CrossOrigin
public class PreferenceController {

    @Autowired
    private PreferenceServiceInterface preferenceService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JwtUtil jwtUtil;

    // GET /user/preferences  → 200 + prefs, or 204 when none (first-login signal)
    @GetMapping
    public ResponseEntity<?> getPreferences(@RequestHeader("Authorization") String authHeader) {
        User user = resolveUser(authHeader);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        UserPreference prefs = preferenceService.getPreferencesByUserId(user.getId());
        if (prefs == null) {
            return ResponseEntity.noContent().build();   // 204
        }
        return ResponseEntity.ok(prefs);
    }

    // POST /user/preferences  → save/update prefs for the authenticated user
    @PostMapping
    public ResponseEntity<?> savePreferences(@RequestBody UserPreference prefs,
                                             @RequestHeader("Authorization") String authHeader) {
        User user = resolveUser(authHeader);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        prefs.setUser_id(user.getId());          // user comes from the token, not the client
        return ResponseEntity.ok(preferenceService.savePreferences(prefs));
    }

    // Returns the user identified by the JWT, or null if the token is missing/invalid
    private User resolveUser(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        try {
            String email = jwtUtil.extractUsername(authHeader.substring(7));
            return userService.findUserByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }
}

