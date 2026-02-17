package enterprise.elroi.exceptions.products;

public class RestoreStockNotFoundException extends ReduceStockNotFoundException {
    public RestoreStockNotFoundException(String message) {
        super(message);
    }
}
