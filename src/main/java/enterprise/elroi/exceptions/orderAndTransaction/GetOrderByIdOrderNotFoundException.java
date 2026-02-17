package enterprise.elroi.exceptions.orderAndTransaction;

public class GetOrderByIdOrderNotFoundException extends CancelOrderNotFoundException {
    public GetOrderByIdOrderNotFoundException(String message) {
        super(message);
    }
}
