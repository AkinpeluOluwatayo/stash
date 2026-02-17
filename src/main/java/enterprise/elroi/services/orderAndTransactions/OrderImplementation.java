package enterprise.elroi.services.orderAndTransactions;

import enterprise.elroi.data.models.Order;
import enterprise.elroi.data.repository.OrderRepository;
import enterprise.elroi.dto.requests.OrderRequests;
import enterprise.elroi.dto.responses.OrderResponses;
import enterprise.elroi.services.logistics.LogisticsInterface;
import enterprise.elroi.utils.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LogisticsImplementation implements LogisticsInterface {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderResponses updateDeliveryAddress(OrderRequests request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getStatus().equals("PENDING")) {
            OrderResponses response = new OrderResponses();
            response.setMessage("Address can only be updated while order is PENDING.");
            response.setSuccess(false);
            return response;
        }

        order.setAddress(request.getAddress());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        OrderResponses response = OrderMapper.toResponse(order);
        response.setMessage("Delivery address updated successfully.");
        response.setSuccess(true);
        return response;
    }

    @Override
    public OrderResponses markAsShipped(OrderRequests request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getStatus().equals("CONFIRMED")) {
            OrderResponses response = new OrderResponses();
            response.setMessage("Order must be CONFIRMED before it can be marked as SHIPPED.");
            response.setSuccess(false);
            return response;
        }

        order.setStatus("SHIPPED");
        order.setShippedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        OrderResponses response = OrderMapper.toResponse(order);
        response.setMessage("Order marked as shipped.");
        response.setSuccess(true);
        return response;
    }

    @Override
    public OrderResponses markAsDelivered(OrderRequests request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getStatus().equals("SHIPPED")) {
            OrderResponses response = new OrderResponses();
            response.setMessage("Order must be SHIPPED before it can be marked as DELIVERED.");
            response.setSuccess(false);
            return response;
        }

        order.setStatus("DELIVERED");
        order.setDeliveredAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        OrderResponses response = OrderMapper.toResponse(order);
        response.setMessage("Order marked as delivered.");
        response.setSuccess(true);
        return response;
    }

    @Override
    public OrderResponses getTrackingStatus(String orderId) {
        Order order = orderRepository.findById(UUID.fromString(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderResponses response = OrderMapper.toResponse(order);
        response.setMessage("Tracking status retrieved.");
        response.setSuccess(true);
        return response;
    }

    @Override
    public boolean validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return false;
        }
        // Basic validation — ensure address is at least 10 characters
        // You can plug in a real address validation API like Google Maps here
        return address.trim().length() >= 10;
    }
}