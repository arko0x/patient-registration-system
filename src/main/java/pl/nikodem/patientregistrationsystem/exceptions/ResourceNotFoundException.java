package pl.nikodem.patientregistrationsystem.exceptions;

public class ResourceNotFoundException extends Exception {
    private final static String MESSAGE = "Resource not found";

    public ResourceNotFoundException() {
        super(MESSAGE);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
