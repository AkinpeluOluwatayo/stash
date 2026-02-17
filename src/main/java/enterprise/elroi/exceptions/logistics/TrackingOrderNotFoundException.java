package enterprise.elroi.exceptions.logistics;

public class TrackingOrderNotFoundException extends RuntimeException {
  public TrackingOrderNotFoundException(String message) {
    super(message);
  }
}
