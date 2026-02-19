package enterprise.elroi.controllers;

import enterprise.elroi.dto.requests.OrderRequests;
import enterprise.elroi.dto.responses.OrderResponses;
import enterprise.elroi.services.logistics.LogisticsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stash/logistics")
@CrossOrigin(origins = "http://localhost:3000")
public class LogisticsController {

    @Autowired
    private LogisticsInterface logisticsService;

    @PutMapping("/update-address")
    public ResponseEntity<OrderResponses> updateDeliveryAddress(@RequestBody OrderRequests request) {
        OrderResponses response = logisticsService.updateDeliveryAddress(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/ship")
    public ResponseEntity<OrderResponses> markAsShipped(@RequestBody OrderRequests request) {
        OrderResponses response = logisticsService.markAsShipped(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/deliver")
    public ResponseEntity<OrderResponses> markAsDelivered(@RequestBody OrderRequests request) {
        OrderResponses response = logisticsService.markAsDelivered(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/track/{orderId}")
    public ResponseEntity<OrderResponses> getTrackingStatus(@PathVariable String orderId) {
        OrderResponses response = logisticsService.getTrackingStatus(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate-address")
    public ResponseEntity<Boolean> validateAddress(@RequestParam String address) {
        boolean isValid = logisticsService.validateAddress(address);
        return ResponseEntity.ok(isValid);
    }
}