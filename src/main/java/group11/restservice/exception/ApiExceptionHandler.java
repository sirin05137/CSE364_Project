package group11.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    // 400 Bad Request
    @ExceptionHandler(InputInvalidException.class)
    public ResponseEntity<ApiError> handleException(final InputInvalidException ex) {
        final ApiError apiError =
                new ApiError("InputInvalidError", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}