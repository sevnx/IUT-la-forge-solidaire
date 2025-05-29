package desforge.dev.errors;

public class BorrowRequestAlreadyExistsException extends RuntimeException {

    public BorrowRequestAlreadyExistsException() {
    }

    public BorrowRequestAlreadyExistsException(String message) {
        super(message);
    }
}
