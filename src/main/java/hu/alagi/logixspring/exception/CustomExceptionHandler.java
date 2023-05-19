package hu.alagi.logixspring.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorEntity> handleValidationError(BindException exception) {
        log.warn(exception.getMessage(), exception);
        List<FieldErrorDto> fieldErrorDtoList =
                exception.getBindingResult().getFieldErrors()
                        .stream()
                        .map(fieldError->new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue()))
                        .toList();
        return new ResponseEntity<>(
                new ValidationErrorEntity(
                        ErrorCode.VALIDATION,
                        exception.getMessage(),
                        fieldErrorDtoList
                        ),
                HttpStatus.BAD_REQUEST
                );
    }

    @ExceptionHandler(EntityAlreadyExistsWithGivenIdException.class)
    public ResponseEntity<DefaultErrorEntity> handleEntityIsAlreadyExistsException(EntityAlreadyExistsWithGivenIdException exception) {
        log.warn(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new DefaultErrorEntity(ErrorCode.ENTITY_ALREADY_EXISTS, exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EntityIdMismatchException.class)
    public ResponseEntity<DefaultErrorEntity> handleEntityIsAlreadyExistsException(EntityIdMismatchException exception) {
        log.warn(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new DefaultErrorEntity(ErrorCode.ENTITY_ID_MISMATCH, exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EntityNotExistsWithGivenIdException.class)
    public ResponseEntity<DefaultErrorEntity> handleEntityNotExistsByGivenIdException(EntityNotExistsWithGivenIdException exception) {
        log.warn(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new DefaultErrorEntity(ErrorCode.ENTITY_NOT_EXISTS, exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InvalidCountryCodeException.class)
    public ResponseEntity<DefaultErrorEntity> handleEmployeeNotBelongsToTheGivenCompanyException(InvalidCountryCodeException exception) {
        log.warn(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new DefaultErrorEntity(ErrorCode.INVALID_COUNTRY_CODE, exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DefaultErrorEntity> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.warn(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new DefaultErrorEntity(ErrorCode.ILLEGAL_ARGUMENT, exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
