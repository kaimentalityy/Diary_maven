package server.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import server.presentation.dto.response.ErrorDto;
import server.utils.exception.badrequest.CustomBadRequestException;
import server.utils.exception.conflict.CustomConflictException;
import server.utils.exception.internalerror.CustomInternalServerErrorException;
import server.utils.exception.notfound.CustomNotFoundException;
import server.utils.exception.unautharized.CustomUnauthorizedException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomBadRequestException.class)
    public ErrorDto handleBadRequestException(CustomBadRequestException e) {
        return new ErrorDto("Bad request: " + e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomConflictException.class)
    public ErrorDto handleConflictException(CustomConflictException e) {
        return new ErrorDto("Conflict: " + e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    public ErrorDto handleNotFoundException(CustomNotFoundException e) {
        return new ErrorDto("Not found: " + e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomUnauthorizedException.class)
    public ErrorDto handleUnauthorizedException(CustomUnauthorizedException e) {
        return new ErrorDto("Unauthorized: " + e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomInternalServerErrorException.class)
    public ErrorDto handleInternalServerErrorException(CustomInternalServerErrorException e) {
        return new ErrorDto("Internal server error: " + e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public ErrorDto handleSQLException(SQLException ex) {
        return new ErrorDto("Database error: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return errors;
    }

}