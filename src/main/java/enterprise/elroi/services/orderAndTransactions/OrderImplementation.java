package enterprise.elroi.services.orderAndTransactions;

import enterprise.elroi.data.models.Order;
import enterprise.elroi.data.repository.OrderRepository;
import enterprise.elroi.dto.requests.OrderRequests;
import enterprise.elroi.dto.responses.OrderResponses;
import enterprise.elroi.exceptions.orderAndTransaction.CancelOrderNotFoundException;
import enterprise.elroi.exceptions.orderAndTransaction.ConfirmOrderNotFoundException;
import enterprise.elroi.exceptions.orderAndTransaction.GetOrderByIdOrderNotFoundException;
import enterprise.elroi.utils.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class OrderImplementation implements OrderInterface {

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
        OrderResponses response = OrderMapper.toResponse(order);
        response.setMessage(message);
        response.setSuccess(true);
        return response;
    }

    private List<OrderResponses> mapToResponseList(Page<Order> orders) {
        return orders.stream()
                .map(order -> buildSuccessResponse(order, null))
                .collect(Collectors.toList());
    }


    @Override
    public OrderResponses placeOrder(OrderRequests request) {
        Order order = new Order();
        order.setBuyerId(request.getBuyerId());
        order.setSellerId(request.getSellerId());
        order.setProductId(request.getProductId());
        order.setAddress(request.getAddress());
        order.setStatus("PENDING");
        order.setOrderedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        return buildSuccessResponse(order, "Order placed successfully.");
    }

    @Override
    public OrderResponses confirmOrder(String orderId) {
        Order order = findOrder(UUID.fromString(orderId),
                () -> new ConfirmOrderNotFoundException("Order not found"));

        if (!order.getStatus().equals("PENDING"))
            return buildErrorResponse("Only PENDING orders can be confirmed.");

        order.setStatus("CONFIRMED");
        order.setConfirmedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        return buildSuccessResponse(order, "Order confirmed successfully.");
    }

    @Override
    public OrderResponses cancelOrder(String orderId) {
        Order order = findOrder(UUID.fromString(orderId),
                () -> new CancelOrderNotFoundException("Order not found"));

        if (order.getStatus().equals("DELIVERED") || order.getStatus().equals("CANCELLED"))
            return buildErrorResponse("Order cannot be cancelled at this stage.");

        order.setStatus("CANCELLED");
        order.setCancelledAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        return buildSuccessResponse(order, "Order cancelled successfully.");
    }

    @Override
    public OrderResponses getOrderById(String orderId) {
        Order order = findOrder(UUID.fromString(orderId),
                () -> new GetOrderByIdOrderNotFoundException("Order not found"));

        return buildSuccessResponse(order, "Order retrieved successfully.");
    }

    @Override
    public List<OrderResponses> getOrders(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return mapToResponseList(orderRepository.findAll(pageable));
    }

    @Override
    public List<OrderResponses> getOrdersByBuyer(String buyerId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return mapToResponseList(orderRepository.findByBuyerId(UUID.fromString(buyerId), pageable));
    }

    @Override
    public List<OrderResponses> getOrdersBySeller(String sellerId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return mapToResponseList(orderRepository.findBySellerId(UUID.fromString(sellerId), pageable));
    }

    @Override
    public List<OrderResponses> getOrdersByStatus(String status, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return mapToResponseList(orderRepository.findByStatus(status, pageable));
    }
}