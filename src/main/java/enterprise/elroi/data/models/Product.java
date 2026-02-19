package enterprise.elroi.data.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID sellerId;
    private String title;         // Frontend was looking for productName
    private String description;
    private double price;
    private int quantityAvailable;
    private String imageUrl;      // <--- ADD THIS LINE
    private String keyword;       // Add this if you are sending it from the frontend
    private boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}