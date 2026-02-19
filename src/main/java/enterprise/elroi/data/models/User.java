package enterprise.elroi.data.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String supabaseId;

    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    @OneToMany(mappedBy = "sellerId", fetch = FetchType.LAZY)
    private List<Product> listings;

    @OneToMany(mappedBy = "buyerId", fetch = FetchType.LAZY)
    private List<Order> purchaseHistory;
}