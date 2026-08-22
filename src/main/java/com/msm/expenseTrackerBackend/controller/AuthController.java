package com.msm.expenseTrackerBackend.controller;

import com.msm.expenseTrackerBackend.model.User;
import com.msm.expenseTrackerBackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HexFormat;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    private String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(d);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class LoginRequest {
        public String username;
        public String password;

        public LoginRequest() {
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest req) {
        if (req == null || req.username == null || req.password == null) {
            return ResponseEntity.badRequest().body("Missing credentials");
        }

        var userOpt = userRepo.findByUsername(req.username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        User user = userOpt.get();
        String hashed = hash(req.password);
        if (!hashed.equals(user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        Instant expiry = Instant.now().plus(7, ChronoUnit.DAYS);
        HashMap<String, Object> resp = new HashMap<>();
        resp.put("message", "login Success");
        resp.put("expiry", expiry.toString());
        return ResponseEntity.ok(resp);
    }
}