package enterprise.elroi.exceptions.products;

public class DeleteProductNotFoundException extends UpdateProductNotFoundException {
    public DeleteProductNotFoundException(String message) {
        super(message);
    }
}
