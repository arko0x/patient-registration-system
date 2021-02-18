package pl.nikodem.patientregistrationsystem.exceptions;

public class TokenNotFoundException extends Exception {
    private final static String MESSAGE = "Token not found";

    public TokenNotFoundException() {
        super(MESSAGE);
    }

    public TokenNotFoundException(String message) {
        super(message);
    }
}
