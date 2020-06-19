package com.sin.orb.exception;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<?> handleNotFound(ResourceNotFoundException e, WebRequest request) {
        return getExceptionResponseEntity(request, HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<?> handleBadCredentials(BadCredentialsException e, WebRequest request) {
        return getExceptionResponseEntity(request, HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
    }

    @ExceptionHandler(value = {ConflictingRequestException.class})
    protected ResponseEntity<?> handleConflict(ConflictingRequestException e, WebRequest request) {
        return getExceptionResponseEntity(request, HttpStatus.CONFLICT, e.getLocalizedMessage());
    }

    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<?> handleBadRequest(BadRequestException e, WebRequest request) {
        return getExceptionResponseEntity(request, HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
    }

    @ExceptionHandler(value = {PropertyReferenceException.class})
    protected ResponseEntity<?> handleNotFound(PropertyReferenceException e, WebRequest request) {
        return getExceptionResponseEntity(request, HttpStatus.UNPROCESSABLE_ENTITY, e.getLocalizedMessage());
    }

    @ExceptionHandler(value = {DateTimeParseException.class})
    protected ResponseEntity<?> handleDateTimeParseException(DateTimeParseException e, WebRequest request) {
        return getExceptionResponseEntity(request, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid datetime format");
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        return getExceptionResponseEntity(request, HttpStatus.UNPROCESSABLE_ENTITY, "Illegal value");
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<?> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        String[] violations = e.getConstraintViolations().stream()
                               .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                               .toArray(String[]::new);
        return getExceptionResponseEntity(request, HttpStatus.BAD_REQUEST, violations);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleUnexpectedException(Exception e, WebRequest request) {
        if (e instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String message = "Sorry, there's a general server issue going on right now";
        return getExceptionResponseEntity(request, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        return getExceptionResponseEntity(request, status, e.getLocalizedMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String message = String.format("%s. Check JSON format", e.getCause().getCause().getLocalizedMessage());
        return getExceptionResponseEntity(request, HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpHeaders headers,
                                                                         HttpStatus status, WebRequest request) {
        return getExceptionResponseEntity(request, status, e.getLocalizedMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String[] messages = e.getBindingResult().getFieldErrors()
                             .stream()
                             .map(error -> error.getField() + ": " + error.getDefaultMessage())
                             .toArray(String[]::new);
        return getExceptionResponseEntity(request, status, messages);
    }

    private ResponseEntity<Object> getExceptionResponseEntity(@NotNull WebRequest request, @NotNull HttpStatus status, String... messages) {
        final Map<String, Object> body = new LinkedHashMap<>();
        final String path = request.getDescription(false);
        final boolean isNotEmpty = ArrayUtils.isNotEmpty(messages);
        final String errorMessages = isNotEmpty ? String.join(", ", messages) : "No message available";

        body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", errorMessages);
        body.put("path", path);

        return new ResponseEntity<>(body, status);
    }
}