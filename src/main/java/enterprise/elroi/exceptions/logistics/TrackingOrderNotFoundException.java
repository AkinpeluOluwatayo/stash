package enterprise.elroi.exceptions.logistics;

public class TrackingOrderNotFoundException extends MarkAsDeliveredOrderNotFoundException {
    public TrackingOrderNotFoundException(String message) {
        super(message);
    }
}
