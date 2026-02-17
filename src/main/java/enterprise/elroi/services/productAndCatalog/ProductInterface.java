package enterprise.elroi.services.productAndCatalog;

import enterprise.elroi.dto.requests.ProductRequests;
import enterprise.elroi.dto.responses.ProductResponses;
import java.util.List;

public interface ProductInterface {

    // Seller creates a new product listing
    ProductResponses createProduct(ProductRequests request);

    // Seller updates an existing listing — ownership verified in implementation
    ProductResponses updateProduct(ProductRequests request);

    // Seller removes a listing — ownership verified in implementation
    ProductResponses deleteProduct(ProductRequests request);

    // Fetch a single product by its id
    ProductResponses getProductById(String productId);

    // Fetch all listings by a specific seller (paginated)
    List<ProductResponses> getProductsBySeller(String sellerId, int page, int pageSize);

    // Search and filter products by keyword, price range, or seller (paginated)
    List<ProductResponses> searchProducts(ProductRequests request);

    // Fetch all available products — used for the main browse/explore feed
    List<ProductResponses> getAllProducts(int page, int pageSize);

    // Reduce stock after an order is placed — returns updated stock state
    ProductResponses reduceStock(String productId, int quantity);

    // Restore stock after an order is cancelled — returns updated stock state
    ProductResponses restoreStock(String productId, int quantity);

    // Check if a product has enough stock for a requested quantity
    boolean isInStock(String productId, int requestedQuantity);
}