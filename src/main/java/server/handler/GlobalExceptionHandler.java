package server.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import server.presentation.dto.response.ErrorDto;
import server.utils.exception.badrequest.BadRequestException;
import server.utils.exception.conflict.ConflictException;
import server.utils.exception.internalerror.InternalServerErrorException;
import server.utils.exception.notfound.NotFoundException;
import server.utils.exception.unautharized.UnauthorizedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException e) {
        return buildError("Bad request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorDto> handleConflictException(ConflictException e) {
        return buildError("Conflict: " + e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException e) {
        return buildError("Not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDto> handleUnauthorizedException(UnauthorizedException e) {
        return buildError("Unauthorized: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorDto> handleInternalServerErrorException(InternalServerErrorException e) {
        return buildError("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorDto> buildError(String message, HttpStatus status) {
        ErrorDto error = new ErrorDto(message, status.value(), status.getReasonPhrase());
        return ResponseEntity.status(status).body(error);
    }

}