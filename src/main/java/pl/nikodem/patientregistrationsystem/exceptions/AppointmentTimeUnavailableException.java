package pl.nikodem.patientregistrationsystem.exceptions;

import java.time.LocalDateTime;

public class AppointmentTimeUnavailableException extends Exception {
    private final static String MESSAGE = "Appointment time unavailable for time: ";

    public AppointmentTimeUnavailableException(LocalDateTime appointmentTime) {
        super(MESSAGE + appointmentTime);
    }

    public AppointmentTimeUnavailableException(String message) {
        super(message);
    }
}
