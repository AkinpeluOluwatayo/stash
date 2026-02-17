package enterprise.elroi.services.orderAndTransactions;

import enterprise.elroi.dto.requests.OrderRequests;
import enterprise.elroi.dto.responses.OrderResponses;
import java.util.List;

public interface OrderInterface {

    OrderResponses placeOrder(OrderRequests request);

    OrderResponses confirmOrder(String orderId);

    OrderResponses cancelOrder(String orderId);

    OrderResponses getOrderById(String orderId);

    List<OrderResponses> getOrders(int page, int pageSize);

    List<OrderResponses> getOrdersByBuyer(String buyerId, int page, int pageSize);

    List<OrderResponses> getOrdersBySeller(String sellerId, int page, int pageSize);

    List<OrderResponses> getOrdersByStatus(String status, int page, int pageSize);
}