package group11.restservice.exception;

import java.util.List;

public class InputInvalidException extends RuntimeException {
    private List<String> message;

    public InputInvalidException(List<String> message) {
        this.message = message;
    }

    public String getMessage() { return String.valueOf(message); }
    /*
    public void setMessage(List<String> message) { this.message = message; }
    */
}