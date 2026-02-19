package enterprise.elroi.controllers;

import enterprise.elroi.services.authentication.AuthInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/stash/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthInterface authService;

    // Combined Sync/Register logic
    @PostMapping("/sync")
    public ResponseEntity<?> syncUser(@RequestBody Map<String, Object> userData) {
        try {
            authService.syncUser(userData);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "User state synchronized with Stash DB"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> userData) {
        authService.syncUser(userData);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.ok(Map.of("isValid", false));
        }
        String token = authHeader.substring(7);
        return ResponseEntity.ok(Map.of("isValid", authService.validateToken(token)));
    }
}