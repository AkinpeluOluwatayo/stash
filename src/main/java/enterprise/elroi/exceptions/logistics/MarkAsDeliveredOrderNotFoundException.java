package enterprise.elroi.exceptions.logistics;

public class MarkAsDeliveredOrderNotFoundException extends RuntimeException {
  public MarkAsDeliveredOrderNotFoundException(String message) {
    super(message);
  }
}
