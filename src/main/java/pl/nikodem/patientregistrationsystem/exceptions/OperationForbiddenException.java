package pl.nikodem.patientregistrationsystem.exceptions;

public class OperationForbiddenException extends Exception {
    private final static String MESSAGE = "Operation forbidden";

    public OperationForbiddenException() {
        super(MESSAGE);
    }

    public OperationForbiddenException(String message) {
        super(message);
    }
}
