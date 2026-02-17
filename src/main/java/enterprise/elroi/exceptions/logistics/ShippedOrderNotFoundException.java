package enterprise.elroi.exceptions.logistics;

public class ShippedOrderNotFoundException extends RuntimeException {
  public ShippedOrderNotFoundException(String message) {
    super(message);
  }
}
