package enterprise.elroi.exceptions.products;

public class InStockNotFoundException extends RuntimeException {
  public InStockNotFoundException(String message) {
    super(message);
  }
}
