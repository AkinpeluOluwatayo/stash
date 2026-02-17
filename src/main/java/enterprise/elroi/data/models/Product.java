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
    private String title;
    private String description;
    private double price;
    private int quantityAvailable;
    private boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}