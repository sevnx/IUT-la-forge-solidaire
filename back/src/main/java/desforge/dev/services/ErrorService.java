package desforge.dev.services;

import desforge.dev.models.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ErrorService implements IErrorService {

    @Override
    public ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(status.value());
        error.setMessage(message);
        return ResponseEntity.status(status).body(error);
    }
}
