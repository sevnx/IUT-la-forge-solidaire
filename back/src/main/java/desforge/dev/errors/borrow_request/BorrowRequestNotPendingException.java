package desforge.dev.errors.borrow_request;

public class BorrowRequestNotPendingException extends RuntimeException {
    public BorrowRequestNotPendingException(String message) {
        super(message);
    }
}
