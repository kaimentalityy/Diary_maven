package server.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import server.presentation.dto.response.ErrorDto;
import server.utils.exception.badrequest.CustomBadRequestException;
import server.utils.exception.conflict.CustomConflictException;
import server.utils.exception.internalerror.CustomInternalServerErrorException;
import server.utils.exception.notfound.CustomNotFoundException;
import server.utils.exception.unautharized.CustomUnauthorizedException;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadRequestException(CustomBadRequestException e) {
        return new ErrorDto("Bad request: " + e.getMessage());
    }

    @ExceptionHandler(CustomConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handleConflictException(CustomConflictException e) {
        return new ErrorDto("Conflict: " + e.getMessage());
    }

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(CustomNotFoundException e) {
        return new ErrorDto("Not found: " + e.getMessage());
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleUnauthorizedException(CustomUnauthorizedException e) {
        return new ErrorDto("Unauthorized: " + e.getMessage());
    }

    @ExceptionHandler(CustomInternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleInternalServerErrorException(CustomInternalServerErrorException e) {
        return new ErrorDto("Internal server error: " + e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleSQLException(SQLException ex) {
        return new ErrorDto("Database error: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((m1, m2) -> m1 + "; " + m2)
                .orElse("Validation failed");

        return new ErrorDto(message);
    }
}
