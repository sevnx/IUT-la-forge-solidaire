package desforge.dev.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RegisterException extends RuntimeException {
    public RegisterException(String message) {
        super(message);
    }
}