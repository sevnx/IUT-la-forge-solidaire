package desforge.dev.errors.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
