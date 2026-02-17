package enterprise.elroi.exceptions.orderAndTransaction;

public class GetOrderByIdOrderNotFoundException extends RuntimeException {
  public GetOrderByIdOrderNotFoundException(String message) {
    super(message);
  }
}
