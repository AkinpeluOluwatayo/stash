package enterprise.elroi.dto.requests;

import lombok.Data;
import java.util.UUID;

@Data
public class ProductRequests {
    private UUID sellerId;
    private String title;
    private String description;
    private double price;
    private int quantityAvailable;
    private String keyword;   // added for search
    private double minPrice;  // added for search
    private double maxPrice;  // added for search
    private String productId; // added for update/delete
    private String imageUrl;
}