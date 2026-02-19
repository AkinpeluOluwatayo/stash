package enterprise.elroi.services.authentication;

import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWKSet;
import enterprise.elroi.data.models.User;
import enterprise.elroi.data.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.ECPublicKey;
import java.util.Map;

@Service
public class AuthImplementation implements AuthInterface {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Value("${supabase.jwt.legacy-secret}")
    private String legacySecret;

    @Autowired
    private UserRepository userRepository;

    private ECPublicKey ecPublicKey;

    @PostConstruct
    public void init() {
        try {
            JWKSet jwkSet = JWKSet.load(new URL(jwkSetUri));
            ECKey ecKey = (ECKey) jwkSet.getKeys().get(0);
            this.ecPublicKey = ecKey.toECPublicKey();
            System.out.println("STASH: EC public key loaded successfully");
        } catch (Exception e) {
            System.err.println("STASH: Failed to load EC public key: " + e.getMessage());
        }
    }

    @Override
    public boolean validateToken(String token) {
        // Try ES256 with raw EC public key
        try {
            Jwts.parserBuilder()
                    .setSigningKey(ecPublicKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.err.println("ES256 FAILED: " + e.getMessage());
        }

        // Fall back to HS256
        try {
            SecretKey key = Keys.hmacShaKeyFor(legacySecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.err.println("HS256 FAILED: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String extractUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ecPublicKey).build()
                    .parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception ignored) {}

        try {
            SecretKey key = Keys.hmacShaKeyFor(legacySecret.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String extractRole(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(ecPublicKey).build()
                    .parseClaimsJws(token).getBody();
        } catch (Exception ignored) {}

        if (claims == null) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(legacySecret.getBytes(StandardCharsets.UTF_8));
                claims = Jwts.parserBuilder().setSigningKey(key).build()
                        .parseClaimsJws(token).getBody();
            } catch (Exception ignored) {}
        }

        if (claims != null) {
            Map<String, Object> appMetadata = (Map<String, Object>) claims.get("app_metadata");
            if (appMetadata != null && appMetadata.get("role") != null)
                return appMetadata.get("role").toString();
        }

        return "USER";
    }

    @Override
    public void syncUser(Map<String, Object> userData) {
        String email = (String) userData.get("email");
        if (email == null) return;

        User user = userRepository.findByEmail(email).orElse(new User());
        user.setEmail(email);
        user.setSupabaseId((String) userData.get("id"));

        if (userData.containsKey("firstName")) user.setFirstName((String) userData.get("firstName"));
        if (userData.containsKey("lastName")) user.setLastName((String) userData.get("lastName"));

        if (user.getRole() == null) user.setRole("USER");

        userRepository.save(user);
    }
}