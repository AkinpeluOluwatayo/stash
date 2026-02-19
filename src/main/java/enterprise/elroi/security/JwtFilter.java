package enterprise.elroi.security;

import enterprise.elroi.services.authentication.AuthInterface;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private AuthInterface authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Handle CORS Pre-flight requests immediately
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        // 2. Check for the Bearer Token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                if (authService.validateToken(token)) {
                    String userId = authService.extractUserId(token);
                    String role = authService.extractRole(token);

                    // --- THE NULL TRAP FIX ---
                    // Default to "USER" if role is missing or null to prevent crashes
                    if (role == null || role.trim().isEmpty()) {
                        role = "USER";
                    }

                    // 3. Create the Authentication object with the ROLE_ prefix
                    // Spring's hasRole("ADMIN") actually looks for "ROLE_ADMIN"
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userId,
                                    null, // No password needed for JWT
                                    List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                            );

                    // 4. Set the security context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Log the error but don't block the chain;
                // SecurityConfig will block unauthorized requests later.
                System.err.println("JWT Filtering failed: " + e.getMessage());
            }
        }

        // Continue to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}