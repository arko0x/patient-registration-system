package pl.nikodem.patientregistrationsystem.exceptions;

import java.time.LocalDateTime;

public class TokenExpiredException extends Exception {
    private static final String MESSAGE = "Token expired at ";

    public TokenExpiredException(LocalDateTime when) {
        super(MESSAGE + when);
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}
