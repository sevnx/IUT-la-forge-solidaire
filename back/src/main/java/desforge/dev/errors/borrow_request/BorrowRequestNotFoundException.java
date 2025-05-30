package desforge.dev.errors.borrow_request;

public class BorrowRequestNotFoundException extends RuntimeException  {
    public BorrowRequestNotFoundException(String message) {
        super(message);
    }
}
