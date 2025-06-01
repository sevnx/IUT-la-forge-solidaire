package desforge.dev.services;

import desforge.dev.models.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IErrorService {
    ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message);
}
