package enterprise.elroi.services.authentication;

import enterprise.elroi.dto.requests.UserRequests;
import enterprise.elroi.dto.responses.UserResponses;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class AuthImplementation implements AuthInterface {

    @Value("${supabase.jwt.secret}")
    private String jwtSecret;

    @Override
    public UserResponses register(UserRequests request) {
        UserResponses response = new UserResponses();
        response.setMessage("Registration handled by Supabase. Syncing local profile...");
        response.setSuccess(true);
        return response;
    }

    @Override
    public UserResponses login(UserRequests request) {
        UserResponses response = new UserResponses();
        response.setMessage("Login verified via JWT.");
        response.setSuccess(true);
        return response;
    }

    @Override
    public void logout(String token) {
    }

    @Override
    public UserResponses changePassword(UserRequests request) {
        UserResponses response = new UserResponses();
        response.setMessage("Password changes must be initiated via Supabase Auth.");
        response.setSuccess(false);
        return response;
    }

    @Override
    public UserResponses deleteAccount(UserRequests request) {
        UserResponses response = new UserResponses();
        response.setMessage("Account deletion request received.");
        response.setSuccess(true);
        response.setDeleted(true);
        return response;
    }

    @Override
    public UserResponses verifyEmail(String token) {
        UserResponses response = new UserResponses();
        response.setMessage("Email verification is managed by Supabase.");
        response.setSuccess(true);
        return response;
    }

    @Override
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String extractUserId(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}