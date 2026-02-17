package enterprise.elroi.exceptions.orderAndTransaction;

public class CancelOrderNotFoundException extends RuntimeException {
  public CancelOrderNotFoundException(String message) {
    super(message);
  }
}
