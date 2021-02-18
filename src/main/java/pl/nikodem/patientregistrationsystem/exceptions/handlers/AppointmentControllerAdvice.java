package pl.nikodem.patientregistrationsystem.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.nikodem.patientregistrationsystem.exceptions.*;

@RestControllerAdvice
public class AppointmentControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({AppointmentDateNotAvailableException.class, AppointmentTimeUnavailableException.class})
    public ResponseEntity<ApplicationError> handleAppointmentDateNotAvailableException(Exception exception) {
        ApplicationError applicationError = ApplicationError.of(exception, HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>(applicationError, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({DoctorNotFoundException.class, ResourceNotFoundException.class, TokenNotFoundException.class})
    public ResponseEntity<ApplicationError> handleNotFoundExceptions(Exception exception) {
        ApplicationError applicationError = ApplicationError.of(exception, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(applicationError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EmailAlreadyConfirmedException.class, EmailAlreadyExistsException.class, UsernameAlreadyExistsException.class})
    public ResponseEntity<ApplicationError> handleConflictExceptions(Exception exception) {
        ApplicationError applicationError = ApplicationError.of(exception, HttpStatus.CONFLICT);

        return new ResponseEntity<>(applicationError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApplicationError> handleTokenExpiredException(Exception exception) {
        ApplicationError applicationError = ApplicationError.of(exception, HttpStatus.EXPECTATION_FAILED);

        return new ResponseEntity<>(applicationError, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(OperationForbiddenException.class)
    public ResponseEntity<ApplicationError> handleForbiddenException(OperationForbiddenException exception) {
        ApplicationError applicationError = ApplicationError.of(exception, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(applicationError, HttpStatus.FORBIDDEN);
    }
}
