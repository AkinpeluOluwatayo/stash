package enterprise.elroi.utils.mapper;

import enterprise.elroi.data.models.User;
import enterprise.elroi.dto.responses.UserResponses;

public class AuthMapper {

    public static UserResponses toResponse(User user) {
        UserResponses response = new UserResponses();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setRole(user.getRole());
        response.setAddress(user.getAddress());
        response.setSuccess(true);
        return response;
    }
}