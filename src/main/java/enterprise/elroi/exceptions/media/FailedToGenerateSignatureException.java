package enterprise.elroi.exceptions.media;

public class FailedToGenerateSignatureException extends RuntimeException {
    public FailedToGenerateSignatureException(String message) {
        super(message);
    }
}
