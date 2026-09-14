package com.demo.tasks.adapter.rest;

import com.demo.tasks.domain.exception.TaskNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TaskNotFoundException.class)
    public ProblemDetail handleTaskNotFound(TaskNotFoundException ex) {
        return problem(HttpStatus.NOT_FOUND, "Task not found", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> fe.getDefaultMessage() == null ? "invalid" : fe.getDefaultMessage(),
                        (a, b) -> a));
        ProblemDetail pd = problem(HttpStatus.BAD_REQUEST, "Validation failed", "One or more fields are invalid");
        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class,
            IllegalArgumentException.class })
    public ProblemDetail handleBadRequest(Exception ex) {
        return problem(HttpStatus.BAD_REQUEST, "Bad request", ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException ex) {
        return problem(HttpStatus.FORBIDDEN, "Forbidden", "Access denied");
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        log.error("Unhandled exception", ex);
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", "An unexpected error occurred");
    }

    private static ProblemDetail problem(HttpStatus status, String title, String detail) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(title);
        return pd;
    }
}