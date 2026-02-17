package enterprise.elroi.dto.responses;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ProductResponses {
    private UUID id;
    private UUID sellerId;
    private String title;
    private String description;
    private double price;
    private int quantityAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;        // added for delete response
    private String message;         // added for delete/stock responses
    private int updatedStock;       // added for stock update response
    private List<ProductResponses> products; // added for list response
    private int totalPages;         // added for list response
    private int currentPage;        // added for list response
    private long totalElements;     // added for list response
}