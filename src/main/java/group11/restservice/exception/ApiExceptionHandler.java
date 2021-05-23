package group11.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InputInvalidException.class)
    public ResponseEntity<ApiErrorResponse> handleException(InputInvalidException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("InputInvalidError", "Entered " + ex.getFieldname() + " input ("+ ex.getInput() +") is invalid.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}