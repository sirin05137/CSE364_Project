package group11.restservice.exception;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class ApiError {
    private String error;
    private List<String> message;

    public ApiError(String error, List<String> message) {
        super();
        this.error = error;
        this.message = message;
    }
    public ApiError(String error, String message) {
        super();
        this.error = error;
        // this can make an error later on - e.g. message containing comma splited when unwanted
        message = message.replace("[", "");
        message = message.replace("]", "");
        this.message = Arrays.asList(message.split(","));
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
    public void setMessage(String message) {
        this.message = Arrays.asList(message);
    }

}