package desforge.dev.errors.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RegisterException extends RuntimeException {
    public RegisterException(String message) {
        super(message);
    }
}
