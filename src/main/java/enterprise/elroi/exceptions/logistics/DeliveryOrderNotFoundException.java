package enterprise.elroi.exceptions.logistics;

public class DeliveryOrderNotFoundException extends RuntimeException {
    public DeliveryOrderNotFoundException(String message) {
        super(message);
    }
}
