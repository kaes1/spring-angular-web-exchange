package pl.polsl.webexchange.errorhandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LoginFailedException.class)
    protected ResponseEntity<Object> handleLoginFailedException(LoginFailedException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(new Date())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return super.handleExceptionInternal(ex, error, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(new Date())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return super.handleExceptionInternal(ex, error, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(InvalidOperationException.class)
    protected ResponseEntity<Object> handleInvalidOperationException(InvalidOperationException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(new Date())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return super.handleExceptionInternal(ex, error, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(NotUniqueException.class)
    protected ResponseEntity<Object> handleNotUniqueException(NotUniqueException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(new Date())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return super.handleExceptionInternal(ex, error, new HttpHeaders(), httpStatus, request);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, HttpStatus httpStatus, @NonNull WebRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(new Date())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(fieldError != null ? fieldError.getDefaultMessage() : "")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return super.handleExceptionInternal(ex, error, headers, httpStatus, request);
    }
}
