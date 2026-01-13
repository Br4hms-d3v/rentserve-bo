package be.brahms.TFE_RentServe.exceptions.global;

import be.brahms.TFE_RentServe.exceptions.dto.ApiError;
import be.brahms.TFE_RentServe.exceptions.email.EmailExistingException;
import be.brahms.TFE_RentServe.exceptions.email.EmailNotFoundException;
import be.brahms.TFE_RentServe.exceptions.user.PseudoExistException;
import be.brahms.TFE_RentServe.exceptions.user.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

/**
 * Global exception handler for the application.
 * This class catches exceptions thrown by controllers and handles them.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final View error;

    /**
     * Default constructor for GlobalExceptionHandler.
     * <p>
     * This constructor is used to create an instance of the exception handler.
     *
     * @param error the view used to show error messages to the user
     */
    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    // Auth

    /**
     * Handles PseudoExistException and sends a 302 FOUND error.
     * <p>
     * This method is called automatically when the pseudo already exist.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (PseudoExistException).
     * @return A response with an apiError and HTTP status 302 (FOUND).
     */
    @ExceptionHandler(PseudoExistException.class)
    public ResponseEntity<ApiError> handlePseudoNotFoundException(PseudoExistException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.FOUND.value(),
                HttpStatus.FOUND.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

    // Email

    /**
     * Handles EmailExistingException and sends a 302 FOUND error.
     * <p>
     * This method is called automatically when the email already exists.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (EmailExistingException).
     * @return A response with an apiError and HTTP status 302 (FOUND).
     */
    @ExceptionHandler(EmailExistingException.class)
    public ResponseEntity<ApiError> handleEmailExistingException(EmailExistingException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.FOUND.value(),
                HttpStatus.FOUND.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.FOUND);
    }

    /**
     * Handles EmailNotFoundException and sends a 404 NOT_FOUND error.
     * <p>
     * This method is called automatically when the email not found.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (EmailNotFoundException).
     * @return A response with an apiError and HTTP status 404 (NOT_FOUND).
     */
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundEmailException(EmailNotFoundException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    // User

    /**
     * Handles errors specific to user operations.
     *
     * @param except the UserException containing the error message
     * @return a response with the error message and HTTP 400 status
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiError> handleUserException(UserException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
