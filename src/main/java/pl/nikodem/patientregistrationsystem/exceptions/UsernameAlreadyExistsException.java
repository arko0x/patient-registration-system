package pl.nikodem.patientregistrationsystem.exceptions;

public class UsernameAlreadyExistsException extends Exception {
    private final static String MESSAGE = "Username already exists";

    public UsernameAlreadyExistsException() {
        super(MESSAGE);
    }

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
