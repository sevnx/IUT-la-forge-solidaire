package desforge.dev.models.error;

public class ErrorResponse {
    private int errorCode;
    private String message;


    public ErrorResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return errorCode;
    }

    public void setErrorCode(int statusCode) {
        this.errorCode = statusCode;
    }
}
