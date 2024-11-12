package ood.usedbookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionErrorMapper> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ExceptionErrorMapper errorDetails = new ExceptionErrorMapper(
                request.getDescription(false), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ISBNAlreadyExistsException.class)
    public ResponseEntity<ExceptionErrorMapper> handleISBNAlreadyExistsException(ISBNAlreadyExistsException ex, WebRequest request) {
        ExceptionErrorMapper errorDetails = new ExceptionErrorMapper(
                request.getDescription(false), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderItemTypeException.class)
    public ResponseEntity<ExceptionErrorMapper> handleOrderItemTypeException(OrderItemTypeException ex, WebRequest request) {
        ExceptionErrorMapper errorDetails = new ExceptionErrorMapper(
                request.getDescription(false), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderProcessingException.class)
    public ResponseEntity<ExceptionErrorMapper> handleOrderProcessingException(OrderProcessingException ex, WebRequest request) {
        ExceptionErrorMapper errorDetails = new ExceptionErrorMapper(
                request.getDescription(false), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyOrderException.class)
    public ResponseEntity<ExceptionErrorMapper> handleEmptyOrderException(EmptyOrderException ex, WebRequest request) {
        ExceptionErrorMapper errorDetails = new ExceptionErrorMapper(
                request.getDescription(false), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ExceptionErrorMapper> handleAuthorizationException(AuthorizationException ex, WebRequest request) {
        ExceptionErrorMapper errorDetails = new ExceptionErrorMapper(
                request.getDescription(false), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BookCopyAlreadySoldException.class)
    public ResponseEntity<ExceptionErrorMapper> handleBookCopyAlreadySoldException(BookCopyAlreadySoldException ex, WebRequest request) {
        ExceptionErrorMapper errorDetails = new ExceptionErrorMapper(
                request.getDescription(false), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(InventoryException.class)
    public ResponseEntity<ExceptionErrorMapper> handleInventoryException(InventoryException ex, WebRequest request) {
        ExceptionErrorMapper errorDetails = new ExceptionErrorMapper(
                request.getDescription(false), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    // Add more exception handlers for other custom exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionErrorMapper> handleGlobalException(Exception ex, WebRequest request) {
        ExceptionErrorMapper errorDetails = new ExceptionErrorMapper(
                request.getDescription(false), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}