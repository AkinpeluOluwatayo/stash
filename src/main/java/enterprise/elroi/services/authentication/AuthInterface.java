package enterprise.elroi.services.authentication;

import enterprise.elroi.dto.requests.UserRequests;
import enterprise.elroi.dto.responses.UserResponses;

public interface AuthInterface {


    UserResponses register(UserRequests request);


    UserResponses login(UserRequests request);


    void logout(String token);


    UserResponses changePassword(UserRequests request);


    UserResponses deleteAccount(UserRequests request);


    UserResponses verifyEmail(String token);


    boolean validateToken(String token);


    String extractUserId(String token);
}