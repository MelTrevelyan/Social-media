package com.meltrevelyan.socialmedia.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppError handleUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage());
        return new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage(), "Unable to find requested user");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppError handlePostNotFoundException(PostNotFoundException e) {
        log.error(e.getMessage());
        return new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage(), "Unable to find requested post");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError handleInvalidJwtException(InvalidJwtException e) {
        log.error(e.getMessage());
        return new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "Token is invalid");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage());
        return new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "Incorrect request parameters");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError handleInvalidRequestException(InvalidRequestException e) {
        log.error(e.getMessage());
        return new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "Invalid request");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AppError handleNotAuthorForbiddenException(NotAuthorForbiddenException e) {
        log.error(e.getMessage());
        return new AppError(HttpStatus.FORBIDDEN.value(), e.getMessage(), "Access is prohibited");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppError handleUnknownException(Throwable e) {
        log.error(e.getMessage());
        return new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "Unknown error has occurred");
    }
}
