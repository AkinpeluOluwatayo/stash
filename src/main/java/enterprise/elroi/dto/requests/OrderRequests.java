package enterprise.elroi.dto.requests;

import lombok.Data;
import java.util.UUID;

@Data
public class OrderRequests {
    private UUID orderId;   // added for update/ship/deliver
    private UUID buyerId;
    private UUID sellerId;
    private UUID productId;
    private String address;
    private String status;  // added for tracking
}