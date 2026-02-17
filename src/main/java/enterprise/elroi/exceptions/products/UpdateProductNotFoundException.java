package enterprise.elroi.exceptions.products;

public class UpdateProductNotFoundException extends RuntimeException {
  public UpdateProductNotFoundException(String message) {
    super(message);
  }
}
