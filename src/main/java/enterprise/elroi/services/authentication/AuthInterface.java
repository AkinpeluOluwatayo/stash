package enterprise.elroi.services.authentication;

import java.util.Map;

public interface AuthInterface {
    boolean validateToken(String token);
    String extractUserId(String token);
    void syncUser(Map<String, Object> userData);
    String extractRole(String token); // This was likely missing
}