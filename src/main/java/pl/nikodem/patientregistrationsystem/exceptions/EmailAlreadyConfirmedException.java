package pl.nikodem.patientregistrationsystem.exceptions;

public class EmailAlreadyConfirmedException extends Exception {
    private static final String MESSAGE = "Email already confirmed";

    public EmailAlreadyConfirmedException() {
        super(MESSAGE);
    }

    public EmailAlreadyConfirmedException(String message) {
        super(message);
    }
}
