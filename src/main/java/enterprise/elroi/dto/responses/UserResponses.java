package enterprise.elroi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponses {
    private UUID id;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String token;
    private boolean deleted;
    private String message;  // add this
    private boolean success; // add this
}