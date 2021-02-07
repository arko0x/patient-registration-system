package pl.nikodem.patientregistrationsystem.exceptions;

public class TokenExpiredException extends Exception {
    public TokenExpiredException() {
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}
