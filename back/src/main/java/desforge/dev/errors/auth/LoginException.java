package desforge.dev.errors.auth;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
