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
    private boolean deleted;
    private String message;
    private int updatedStock;
    private List<ProductResponses> products;
    private int totalPages;
    private int currentPage;
    private long totalElements;
    private String imageUrl;
}