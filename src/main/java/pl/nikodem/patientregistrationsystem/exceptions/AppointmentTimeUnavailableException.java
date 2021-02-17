package pl.nikodem.patientregistrationsystem.exceptions;

public class AppointmentTimeUnavailableException extends Exception {
    public AppointmentTimeUnavailableException() {
    }

    public AppointmentTimeUnavailableException(String message) {
        super(message);
    }
}
