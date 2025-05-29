package desforge.dev.errors;

public class CannotBorrowOwnToolException extends RuntimeException {
    public CannotBorrowOwnToolException(String message) {
        super(message);
    }
}
