package enterprise.elroi.controllers;

import enterprise.elroi.dto.requests.ProductRequests;
import enterprise.elroi.dto.responses.ProductResponses;
import enterprise.elroi.services.productAndCatalog.ProductInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stash/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductInterface productService;

    @PostMapping("/create")
    public ResponseEntity<ProductResponses> createProduct(@RequestBody ProductRequests request) {
        ProductResponses response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponses> updateProduct(@RequestBody ProductRequests request) {
        ProductResponses response = productService.updateProduct(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ProductResponses> deleteProduct(@RequestBody ProductRequests request) {
        ProductResponses response = productService.deleteProduct(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponses> getProductById(@PathVariable String productId) {
        ProductResponses response = productService.getProductById(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ProductResponses>> getProductsBySeller(
            @PathVariable String sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<ProductResponses> response = productService.getProductsBySeller(sellerId, page, pageSize);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ProductResponses>> searchProducts(@RequestBody ProductRequests request) {
        List<ProductResponses> response = productService.searchProducts(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponses>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<ProductResponses> response = productService.getAllProducts(page, pageSize);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reduce-stock/{productId}")
    public ResponseEntity<ProductResponses> reduceStock(
            @PathVariable String productId,
            @RequestParam int quantity) {
        ProductResponses response = productService.reduceStock(productId, quantity);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/restore-stock/{productId}")
    public ResponseEntity<ProductResponses> restoreStock(
            @PathVariable String productId,
            @RequestParam int quantity) {
        ProductResponses response = productService.restoreStock(productId, quantity);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/in-stock/{productId}")
    public ResponseEntity<Boolean> isInStock(
            @PathVariable String productId,
            @RequestParam int requestedQuantity) {
        boolean inStock = productService.isInStock(productId, requestedQuantity);
        return ResponseEntity.ok(inStock);
    }
}