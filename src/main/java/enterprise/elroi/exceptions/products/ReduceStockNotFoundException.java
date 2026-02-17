package enterprise.elroi.exceptions.products;

public class ReduceStockNotFoundException extends RuntimeException {
  public ReduceStockNotFoundException(String message) {
    super(message);
  }
}
