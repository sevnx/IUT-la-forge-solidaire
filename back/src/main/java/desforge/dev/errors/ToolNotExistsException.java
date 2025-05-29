package desforge.dev.errors;

public class ToolNotExistsException extends RuntimeException {

    public ToolNotExistsException() {
    }

    public ToolNotExistsException(String message) {
        super(message);
    }
}
