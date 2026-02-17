package enterprise.elroi.exceptions.orderAndTransaction;

public class ConfirmOrderNotFoundException extends RuntimeException {
    public ConfirmOrderNotFoundException(String message) {
        super(message);
    }
}
