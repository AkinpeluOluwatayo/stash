package enterprise.elroi.exceptions.products;

public class RestoreStockNotFoundException extends RuntimeException {
  public RestoreStockNotFoundException(String message) {
    super(message);
  }
}
