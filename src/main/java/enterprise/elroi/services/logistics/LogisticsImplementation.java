package enterprise.elroi.services.logistics;

import enterprise.elroi.data.models.Order;
import enterprise.elroi.data.repository.OrderRepository;
import enterprise.elroi.dto.requests.OrderRequests;
import enterprise.elroi.dto.responses.OrderResponses;
import enterprise.elroi.exceptions.logistics.DeliveryOrderNotFoundException;
import enterprise.elroi.exceptions.logistics.MarkAsDeliveredOrderNotFoundException;
import enterprise.elroi.exceptions.logistics.ShippedOrderNotFoundException;
import enterprise.elroi.exceptions.logistics.TrackingOrderNotFoundException;
import enterprise.elroi.utils.mapper.LogisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class LogisticsImplementation implements LogisticsInterface {

    @Autowired
    private OrderRepository orderRepository;

    private Order findOrder(UUID id, Supplier<? extends RuntimeException> exceptionSupplier) {
        return orderRepository.findById(id).orElseThrow(exceptionSupplier);
    }

    private OrderResponses buildErrorResponse(String message) {
        OrderResponses response = new OrderResponses();
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }

    private OrderResponses buildSuccessResponse(Order order, String message) {
        OrderResponses response = LogisticsMapper.toResponse(order);
        response.setMessage(message);
        response.setSuccess(true);
        return response;
    }

    @Override
    public OrderResponses updateDeliveryAddress(OrderRequests request) {
        Order order = findOrder(request.getOrderId(),
                () -> new DeliveryOrderNotFoundException("Order not found"));

        if (!order.getStatus().equals("PENDING"))
            return buildErrorResponse("Address can only be updated while order is PENDING.");

        order.setAddress(request.getAddress());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        return buildSuccessResponse(order, "Delivery address updated successfully.");
    }

    @Override
    public OrderResponses markAsShipped(OrderRequests request) {
        Order order = findOrder(request.getOrderId(),
                () -> new ShippedOrderNotFoundException("Order not found"));

        if (!order.getStatus().equals("CONFIRMED"))
            return buildErrorResponse("Order must be CONFIRMED before it can be marked as SHIPPED.");

        order.setStatus("SHIPPED");
        order.setShippedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        return buildSuccessResponse(order, "Order marked as shipped.");
    }

    @Override
    public OrderResponses markAsDelivered(OrderRequests request) {
        Order order = findOrder(request.getOrderId(),
                () -> new MarkAsDeliveredOrderNotFoundException("Order not found"));

        if (!order.getStatus().equals("SHIPPED"))
            return buildErrorResponse("Order must be SHIPPED before it can be marked as DELIVERED.");

        order.setStatus("DELIVERED");
        order.setDeliveredAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        return buildSuccessResponse(order, "Order marked as delivered.");
    }

    @Override
    public OrderResponses getTrackingStatus(String orderId) {
        Order order = findOrder(UUID.fromString(orderId),
                () -> new TrackingOrderNotFoundException("Order not found"));

        return buildSuccessResponse(order, "Tracking status retrieved.");
    }

    @Override
    public boolean validateAddress(String address) {
        return address != null && address.trim().length() >= 10;
    }
}