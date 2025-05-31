package desforge.dev.errors.borrow_request;

public class BorrowRequestAlreadyExistsException extends RuntimeException {

    public BorrowRequestAlreadyExistsException(String message) {
        super(message);
    }
}
