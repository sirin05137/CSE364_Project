package group11.restservice.exception;

public class NoDBException extends RuntimeException {
    private String message;

    public NoDBException(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    //public void setMessage() { this.message = message; }
}
