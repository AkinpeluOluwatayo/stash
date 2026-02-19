package enterprise.elroi.controllers;

import enterprise.elroi.dto.requests.OrderRequests;
import enterprise.elroi.dto.responses.OrderResponses;
import enterprise.elroi.services.orderAndTransactions.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stash/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderAndTransactionController {

    @Autowired
    private OrderInterface orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponses> placeOrder(@RequestBody OrderRequests request) {
        OrderResponses response = orderService.placeOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/confirm/{orderId}")
    public ResponseEntity<OrderResponses> confirmOrder(@PathVariable String orderId) {
        OrderResponses response = orderService.confirmOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<OrderResponses> cancelOrder(@PathVariable String orderId) {
        OrderResponses response = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponses> getOrderById(@PathVariable String orderId) {
        OrderResponses response = orderService.getOrderById(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponses>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<OrderResponses> response = orderService.getOrders(page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<OrderResponses>> getOrdersByBuyer(
            @PathVariable String buyerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<OrderResponses> response = orderService.getOrdersByBuyer(buyerId, page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<OrderResponses>> getOrdersBySeller(
            @PathVariable String sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<OrderResponses> response = orderService.getOrdersBySeller(sellerId, page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponses>> getOrdersByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<OrderResponses> response = orderService.getOrdersByStatus(status, page, pageSize);
        return ResponseEntity.ok(response);
    }
}