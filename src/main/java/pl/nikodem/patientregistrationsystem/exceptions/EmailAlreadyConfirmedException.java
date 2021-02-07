package pl.nikodem.patientregistrationsystem.exceptions;

public class EmailAlreadyConfirmedException extends Exception {
    public EmailAlreadyConfirmedException() {
    }

    public EmailAlreadyConfirmedException(String message) {
        super(message);
    }
}
