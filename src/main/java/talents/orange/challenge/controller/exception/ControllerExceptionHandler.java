package talents.orange.challenge.controller.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import talents.orange.challenge.service.exception.UniqueViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {

    private ResponseEntity<ApiError> handleResponse(ApiError err) {
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ApiError> handleObjectNotFound(ObjectNotFoundException e) {
        return handleResponse(ApiError.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message("Recurso %s não encontrado com ID %s".formatted(e.getEntityName(), e.getIdentifier()))
            .build());
    }

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<ApiError> handleUniqueViolation(UniqueViolationException e) {
        return handleResponse(ApiError.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(e.getLocalizedMessage())
            .build());
    }
}
