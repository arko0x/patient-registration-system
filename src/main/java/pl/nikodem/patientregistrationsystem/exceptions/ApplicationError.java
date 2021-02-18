package pl.nikodem.patientregistrationsystem.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApplicationError {
    private HttpStatus httpStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private ApplicationError(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }

    public static ApplicationError of(Exception exception, HttpStatus httpStatus) {
        return new ApplicationError(httpStatus, exception.getMessage());
    }
}
