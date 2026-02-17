package enterprise.elroi.exceptions.logistics;

public class MarkAsDeliveredOrderNotFoundException extends ShippedOrderNotFoundException {
    public MarkAsDeliveredOrderNotFoundException(String message) {
        super(message);
    }
}
