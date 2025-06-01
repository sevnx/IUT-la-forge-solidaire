package desforge.dev.errors.auth;

public class RegisterException extends RuntimeException {
    public RegisterException(String message) {
        super(message);
    }
}
