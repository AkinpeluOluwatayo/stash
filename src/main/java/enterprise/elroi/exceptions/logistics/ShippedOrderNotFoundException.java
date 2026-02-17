package enterprise.elroi.exceptions.logistics;

public class ShippedOrderNotFoundException extends DeliveryOrderNotFoundException {
    public ShippedOrderNotFoundException(String message) {
        super(message);
    }
}
