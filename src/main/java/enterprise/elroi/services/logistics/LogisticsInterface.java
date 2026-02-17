package enterprise.elroi.services.logistics;

import enterprise.elroi.dto.requests.OrderRequests;
import enterprise.elroi.dto.responses.OrderResponses;

public interface LogisticsInterface {


    OrderResponses updateDeliveryAddress(OrderRequests request);


    OrderResponses markAsShipped(OrderRequests request);


    OrderResponses markAsDelivered(OrderRequests request);


    OrderResponses getTrackingStatus(String orderId);

    boolean validateAddress(String address);
}