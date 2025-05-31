package desforge.dev.errors.tools;

public class ToolAlreadyBorrowedException extends RuntimeException {
    public ToolAlreadyBorrowedException(String message) {
        super(message);
    }
}
