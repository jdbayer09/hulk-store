package com.softcaribbean.hulkstore.config;

import com.softcaribbean.hulkstore.api.models.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@RestControllerAdvice
@Slf4j
public class HulkStoreControllerAdvice {
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleUncaughtException(Throwable t) {
        return buildErrorResponse(t, t.getMessage(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(PRECONDITION_FAILED)
    public ResponseEntity<ErrorResponse> invalidRequestErrorHandler(final MethodArgumentNotValidException e) {

        var errors =
                e.getBindingResult().getAllErrors().stream()
                        .filter(Objects::nonNull)
                        .map(this::getValidationErrorMessage)
                        .collect(Collectors.toList());
        return buildErrorResponse(e, String.join(", ", errors), PRECONDITION_FAILED);
    }

    private String getValidationErrorMessage(final ObjectError error) {
        final var errorMessage = new StringBuilder();
        if (error instanceof FieldError fe) {
            errorMessage.append("<").append(fe.getField()).append("> - ");
        }
        errorMessage.append(error.getDefaultMessage());
        return errorMessage.toString();
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleObjectNotFoundException(final ObjectNotFoundException t) {
        return buildErrorResponse(t, t.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(PRECONDITION_FAILED)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(final IllegalArgumentException t) {
        return buildErrorResponse(t, t.getMessage(), PRECONDITION_FAILED);
    }

    /**
     * Builds the {@code ErrorResponse} object to serve all error request and response generic message
     *
     * @param e          Exception thrown by the handler itself
     * @param message    Message to be shown in the consumer request
     * @param httpStatus HTTP status to be sent it to the consumer
     * @return ErrorRepose
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Throwable e, String message, HttpStatus httpStatus) {
        log.error(ExceptionUtils.getStackTrace(e));
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(message, httpStatus.value()));
    }

}
