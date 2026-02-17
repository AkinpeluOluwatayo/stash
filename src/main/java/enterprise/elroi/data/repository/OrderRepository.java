package enterprise.elroi.data.repository;

import enterprise.elroi.data.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Page<Order> findBySellerId(UUID sellerId, Pageable pageable);
    Page<Order> findByBuyerId(UUID buyerId, Pageable pageable);
    Page<Order> findByStatus(String status, Pageable pageable);
}