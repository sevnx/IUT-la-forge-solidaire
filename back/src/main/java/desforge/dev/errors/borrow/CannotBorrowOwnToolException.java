package desforge.dev.errors.borrow;

public class CannotBorrowOwnToolException extends RuntimeException {
    public CannotBorrowOwnToolException(String message) {
        super(message);
    }
}
