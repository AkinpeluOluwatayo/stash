package enterprise.elroi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponses {
    private UUID id;
    private UUID buyerId;
    private UUID sellerId;
    private UUID productId;
    private String address;
    private String status;
    private String message;  // added for feedback
    private boolean success; // added for feedback
    private LocalDateTime orderedAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime cancelledAt;
    private LocalDateTime updatedAt;
}