package pl.nikodem.patientregistrationsystem.exceptions;

public class EmailAlreadyExistsException extends Exception {
    private final static String MESSAGE = "Email already exists";

    public EmailAlreadyExistsException() {
        super(MESSAGE);
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
