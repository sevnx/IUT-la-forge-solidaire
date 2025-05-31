package desforge.dev.errors.borrow_request;

public class NotAllowedStateBorrowRequestException extends RuntimeException {
    public NotAllowedStateBorrowRequestException(String message) {
        super(message);
    }
}
