package group11.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.util.MultiValueMap;


@ControllerAdvice
public class ApiExceptionHandler {


    // 400 Bad Request
    @ExceptionHandler(InputInvalidException.class)
    public ResponseEntity<ApiError> handleException(final InputInvalidException ex) {
        final ApiError apiError =
                new ApiError("InputInvalidError", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    /*
    // 404 Not Found
    @ExceptionHandler(NoDBException.class)
    ResponseEntity<ApiError> handleException(final NoDBException ex) {
        final ApiError apiError =
                new ApiError("NoDBError", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
    */
}