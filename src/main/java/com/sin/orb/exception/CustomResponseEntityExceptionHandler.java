package com.sin.orb.exception;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<?> handleNotFound(RuntimeException e, WebRequest request) {
        String body = String.format("%s. You may have entered an incorrect resource id.", e.getMessage());
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handle(Exception e, WebRequest request) {
        if (e instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String body = "Sorry, there's a general server issue going on right now.";
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> validationErrors = exception.getBindingResult()
                                                 .getFieldErrors()
                                                 .stream()
                                                 .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                                 .collect(Collectors.toList());
        return getExceptionResponseEntity(request, validationErrors);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception, WebRequest request) {
        List<String> validationErrors = exception.getConstraintViolations()
                                                 .stream()
                                                 .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                                                 .collect(Collectors.toList());
        return getExceptionResponseEntity(request, validationErrors);
    }

    private ResponseEntity<Object> getExceptionResponseEntity(WebRequest request, List<String> errors) {
        final Map<String, Object> body = new LinkedHashMap<>();
        final String path = request.getDescription(false);
        final String errorsMessage = CollectionUtils.isNotEmpty(errors) ? errors
                .stream()
                .filter(e -> !StringUtils.isEmpty(e))
                .collect(Collectors.joining(",")) : HttpStatus.BAD_REQUEST.getReasonPhrase();

        body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("errors", errorsMessage);
        body.put("path", path);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}