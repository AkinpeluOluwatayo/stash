package enterprise.elroi.utils.mapper;

import enterprise.elroi.data.models.Order;
import enterprise.elroi.dto.responses.OrderResponses;

public class OrderMapper {

    public static OrderResponses toResponse(Order order) {
        OrderResponses response = new OrderResponses();
        response.setId(order.getId());
        response.setBuyerId(order.getBuyerId());
        response.setSellerId(order.getSellerId());
        response.setProductId(order.getProductId());
        response.setAddress(order.getAddress());
        response.setStatus(order.getStatus());
        response.setOrderedAt(order.getOrderedAt());
        response.setConfirmedAt(order.getConfirmedAt());
        response.setShippedAt(order.getShippedAt());
        response.setDeliveredAt(order.getDeliveredAt());
        response.setCancelledAt(order.getCancelledAt());
        response.setUpdatedAt(order.getUpdatedAt());
        return response;
    }
}