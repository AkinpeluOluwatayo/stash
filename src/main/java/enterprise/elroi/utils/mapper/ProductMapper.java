package enterprise.elroi.utils.mapper;

import enterprise.elroi.data.models.Product;
import enterprise.elroi.dto.responses.ProductResponses;

public class ProductMapper {

    public static ProductResponses toResponse(Product product) {
        ProductResponses response = new ProductResponses();
        // Mapping Entity ID to Response ID
        response.setId(product.getId());
        response.setSellerId(product.getSellerId());
        response.setTitle(product.getTitle());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantityAvailable(product.getQuantityAvailable());

        // FIX: Mapping the Image URL from Entity to Response
        response.setImageUrl(product.getImageUrl());

        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }
}