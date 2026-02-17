package enterprise.elroi.exceptions.orderAndTransaction;

public class CancelOrderNotFoundException extends ConfirmOrderNotFoundException {
    public CancelOrderNotFoundException(String message) {
        super(message);
    }
}
