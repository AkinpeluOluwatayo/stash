package enterprise.elroi.exceptions.products;

public class DeleteProductNotFoundException extends RuntimeException {
  public DeleteProductNotFoundException(String message) {
    super(message);
  }
}
