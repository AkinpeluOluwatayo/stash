package enterprise.elroi.data.repository;

import enterprise.elroi.data.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findBySellerId(UUID sellerId, Pageable pageable);

    Page<Product> findByDeletedFalse(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:keyword IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:sellerId IS NULL OR p.sellerId = :sellerId) AND " +
            "p.price >= :minPrice AND p.price <= :maxPrice AND " +
            "p.deleted = false")
    Page<Product> searchProducts(String keyword, UUID sellerId, double minPrice, double maxPrice, Pageable pageable);
}