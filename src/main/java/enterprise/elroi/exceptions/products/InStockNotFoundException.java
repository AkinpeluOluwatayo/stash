package enterprise.elroi.exceptions.products;

public class InStockNotFoundException extends ReduceStockNotFoundException {
    public InStockNotFoundException(String message) {
        super(message);
    }
}
