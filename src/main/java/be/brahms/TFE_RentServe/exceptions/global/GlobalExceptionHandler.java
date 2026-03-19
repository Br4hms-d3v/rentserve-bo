package be.brahms.TFE_RentServe.exceptions.global;

import be.brahms.TFE_RentServe.exceptions.category.*;
import be.brahms.TFE_RentServe.exceptions.database.DatabaseConstraintException;
import be.brahms.TFE_RentServe.exceptions.dto.ApiError;
import be.brahms.TFE_RentServe.exceptions.email.EmailException;
import be.brahms.TFE_RentServe.exceptions.email.EmailExistingException;
import be.brahms.TFE_RentServe.exceptions.email.EmailNotFoundException;
import be.brahms.TFE_RentServe.exceptions.favor.FavorAlreadyExistingException;
import be.brahms.TFE_RentServe.exceptions.favor.FavorException;
import be.brahms.TFE_RentServe.exceptions.favor.FavorNotEmptyException;
import be.brahms.TFE_RentServe.exceptions.favor.FavorNotFoundException;
import be.brahms.TFE_RentServe.exceptions.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

/**
 * Global exception handler for the application.
 * This class catches exceptions thrown by controllers and handles them.
 * Exception :
 * - Authenticate
 * - Email
 * - User
 *
 * @author Brahim K
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

    /**
     * Handles AccountNotActivatedException and sends a 403 Forbidden error.
     * <p>
     * This method is called automatically when the user doesn't activate the account
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (AccountNotActivatedException).
     * @return A response with an apiError and HTTP status 403 (FORBIDDEN).
     */
    @ExceptionHandler(AccountNotActivatedException.class)
    public ResponseEntity<ApiError> handleAccountNotActivatedException(AccountNotActivatedException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles AccessNotAuthorizedException and sends a 403 Forbidden error.
     * <p>
     * This method is called automatically when the user try to use another identifier to check or update
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (AccessNotAuthorizedException).
     * @return A response with an apiError and HTTP status 401 (UNAUTHORIZED).
     */
    @ExceptionHandler(AccessNotAuthorizedException.class)
    public ResponseEntity<ApiError> handleAccessNotAuthorizedException(AccessNotAuthorizedException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    // Email

    /**
     * Handles errors specific to email operations.
     *
     * @param except the EmailException containing the error message
     * @return a response with the error message and HTTP 400 status
     */
    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ApiError> handleEmailException(EmailException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

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

    /**
     * Handles the InvalidPasswordException and sends a 401 Unauthorized error.
     * <p>
     * This method is called automatically when the user gives a wrong password.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except the exception that was thrown (InvalidPasswordException)
     * @return a ResponseEntity with an ApiError and HTTP status 401 (Unauthorized)
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiError> handleInvalidPasswordException(InvalidPasswordException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles PseudoNotFoundException and sends a 404 NOT_FOUND error.
     * <p>
     * This method is called automatically when the pseudo doesn't exist.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (PseudoNotFoundException).
     * @return A response with an apiError and HTTP status 404 (NOT_FOUND).
     */
    @ExceptionHandler(PseudoNotFoundException.class)
    public ResponseEntity<ApiError> handlePseudoNotFoundException(PseudoNotFoundException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles UserNotFoundException and sends a 404 NOT_FOUND error.
     * <p>
     * This method is called automatically when the user doesn't exist.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (UserNotFoundException).
     * @return A response with an apiError and HTTP status 404 (NOT_FOUND).
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    // Category

    /**
     * Handles errors specific to category operations.
     *
     * @param except the CategoryException containing the error message
     * @return a response with the error message and HTTP 400 status
     */
    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<ApiError> handleCategoryException(CategoryException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles CategoryNotFoundException and sends a 404 NOT_FOUND error.
     * <p>
     * This method is called automatically when the category doesn't exist.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (CategoryNotFoundException).
     * @return A response with an apiError and HTTP status 404 (NOT_FOUND).
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFoundException(CategoryNotFoundException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles CategoryExistingException and sends a 302 FOUND error.
     * <p>
     * This method is called automatically when the category already exists.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (CategoryExistingException).
     * @return A response with an apiError and HTTP status 302 (FOUND).
     */
    @ExceptionHandler(CategoryExistingException.class)
    public ResponseEntity<ApiError> handleCategoryExistingException(CategoryExistingException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.FOUND.value(),
                HttpStatus.FOUND.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.FOUND);
    }

    /**
     * Handles CategoryNotEmptyException and sends a 400 BAD REQUEST error.
     * <p>
     * This method is called automatically when the category is empty.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (CategoryNotEmptyException).
     * @return A response with an apiError and HTTP status 400 (BAD REQUEST).
     */
    @ExceptionHandler(CategoryNotEmptyException.class)
    public ResponseEntity<ApiError> handleCategoryNotEmptyException(CategoryNotEmptyException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles CategoryNotExistingException and sends a 404 NOT FOUND error.
     * <p>
     * This method is called automatically when the category is not existing id DB.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (CategoryNotExistingException).
     * @return A response with an apiError and HTTP status 404 (NOT FOUND).
     */
    @ExceptionHandler(CategoryNotExistingException.class)
    public ResponseEntity<ApiError> handleCategoryNotExistingException(CategoryNotExistingException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    // Favor

    /**
     * Handles errors specific to favor operations.
     *
     * @param except the FavorException containing the error message
     * @return a response with the error message and HTTP 400 status
     */
    @ExceptionHandler(FavorException.class)
    public ResponseEntity<ApiError> handleFavorException(FavorException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles FavorNotFoundException and sends a 404 NOT_FOUND error.
     * <p>
     * This method is called automatically when the favor doesn't exist.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (FavorNotFoundException).
     * @return A response with an apiError and HTTP status 404 (NOT_FOUND).
     */
    @ExceptionHandler(FavorNotFoundException.class)
    public ResponseEntity<ApiError> handleFavorNotFoundException(FavorNotFoundException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles FavorAlreadyExistingException and sends a 302 FOUND error.
     * <p>
     * This method is called automatically when the favor already exists.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (FavorAlreadyExistingException).
     * @return A response with an apiError and HTTP status 302 (FOUND).
     */
    @ExceptionHandler(FavorAlreadyExistingException.class)
    public ResponseEntity<ApiError> handleFavorAlreadyExistingException(FavorAlreadyExistingException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.FOUND.value(),
                HttpStatus.FOUND.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.FOUND);
    }

    /**
     * Handles FavorNotEmptyException and sends a BAD REQUEST error.
     * <p>
     * This method is called automatically when the category is empty.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (FavorNotEmptyException).
     * @return A response with an apiError and HTTP status 400 (BAD REQUEST).
     */
    @ExceptionHandler(FavorNotEmptyException.class)
    public ResponseEntity<ApiError> handleFavorNotEmptyException(FavorNotEmptyException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // Database

    /**
     * Handles DatabaseConstraintException and sends a BAD REQUEST error.
     * <p>
     * This method is called automatically when the user delete a category who's used by another data.
     * It creates an ApiError and sends it to the frontend.
     *
     * @param except The exception that was thrown (DatabaseConstraintException).
     * @return A response with an apiError and HTTP status 400 (BAD REQUEST).
     */
    @ExceptionHandler(DatabaseConstraintException.class)
    public ResponseEntity<ApiError> handleDatabaseConstraintException(DatabaseConstraintException except) {
        ApiError apiError = ApiError.of(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                except.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


}
