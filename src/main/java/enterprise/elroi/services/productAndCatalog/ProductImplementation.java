package enterprise.elroi.services.productAndCatalog;

import enterprise.elroi.data.models.Product;
import enterprise.elroi.data.repository.ProductRepository;
import enterprise.elroi.dto.requests.ProductRequests;
import enterprise.elroi.dto.responses.ProductResponses;
import enterprise.elroi.exceptions.products.*;
import enterprise.elroi.utils.mapper.ProductMapper;
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
public class ProductImplementation implements ProductInterface {

    @Autowired
    private ProductRepository productRepository;

    private Product findProduct(UUID id, Supplier<? extends RuntimeException> exceptionSupplier) {
        return productRepository.findById(id).orElseThrow(exceptionSupplier);
    }

    private ProductResponses buildErrorResponse(String message) {
        ProductResponses response = new ProductResponses();
        response.setMessage(message);
        return response;
    }

    private ProductResponses buildSuccessResponse(Product product, String message) {
        ProductResponses response = ProductMapper.toResponse(product);
        response.setMessage(message);
        return response;
    }

    private List<ProductResponses> mapToResponseList(Page<Product> products) {
        return products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponses createProduct(ProductRequests request) {
        Product product = new Product();
        product.setSellerId(request.getSellerId());
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantityAvailable(request.getQuantityAvailable());

        // FIX: Explicitly setting the Image URL
        product.setImageUrl(request.getImageUrl());

        product.setDeleted(false);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        return buildSuccessResponse(product, "Product created successfully.");
    }

    @Override
    public ProductResponses updateProduct(ProductRequests request) {
        Product product = findProduct(UUID.fromString(request.getProductId()),
                () -> new UpdateProductNotFoundException("Product not found"));

        if (product.isDeleted())
            return buildErrorResponse("Cannot update a deleted product.");

        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantityAvailable(request.getQuantityAvailable());

        // FIX: Allow updating the Image URL if provided
        if (request.getImageUrl() != null) {
            product.setImageUrl(request.getImageUrl());
        }

        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        return buildSuccessResponse(product, "Product updated successfully.");
    }

    @Override
    public ProductResponses deleteProduct(ProductRequests request) {
        Product product = findProduct(UUID.fromString(request.getProductId()),
                () -> new DeleteProductNotFoundException("Product not found"));

        product.setDeleted(true);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        ProductResponses response = buildSuccessResponse(product, "Product deleted successfully.");
        response.setDeleted(true);
        return response;
    }

    @Override
    public ProductResponses getProductById(String productId) {
        Product product = findProduct(UUID.fromString(productId),
                () -> new GetPrductByIdNotFoundException("Product not found"));

        return buildSuccessResponse(product, "Product retrieved successfully.");
    }

    @Override
    public List<ProductResponses> getProductsBySeller(String sellerId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return mapToResponseList(productRepository.findBySellerId(UUID.fromString(sellerId), pageable));
    }

    @Override
    public List<ProductResponses> searchProducts(ProductRequests request) {
        Pageable pageable = PageRequest.of(0, 20);
        return mapToResponseList(productRepository.searchProducts(
                request.getKeyword(),
                request.getSellerId(),
                request.getMinPrice(),
                request.getMaxPrice() == 0 ? Double.MAX_VALUE : request.getMaxPrice(),
                pageable
        ));
    }

    @Override
    public List<ProductResponses> getAllProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return mapToResponseList(productRepository.findByDeletedFalse(pageable));
    }

    @Override
    public ProductResponses reduceStock(String productId, int quantity) {
        Product product = findProduct(UUID.fromString(productId),
                () -> new ReduceStockNotFoundException("Product not found"));

        if (product.getQuantityAvailable() < quantity)
            return buildErrorResponse("Insufficient stock.");

        product.setQuantityAvailable(product.getQuantityAvailable() - quantity);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        ProductResponses response = buildSuccessResponse(product, "Stock reduced successfully.");
        response.setUpdatedStock(product.getQuantityAvailable());
        return response;
    }

    @Override
    public ProductResponses restoreStock(String productId, int quantity) {
        Product product = findProduct(UUID.fromString(productId),
                () -> new RestoreStockNotFoundException("Product not found"));

        product.setQuantityAvailable(product.getQuantityAvailable() + quantity);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        ProductResponses response = buildSuccessResponse(product, "Stock restored successfully.");
        response.setUpdatedStock(product.getQuantityAvailable());
        return response;
    }

    @Override
    public boolean isInStock(String productId, int requestedQuantity) {
        Product product = findProduct(UUID.fromString(productId),
                () -> new InStockNotFoundException("Product not found"));

        return product.getQuantityAvailable() >= requestedQuantity;
    }
}