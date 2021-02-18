package pl.nikodem.patientregistrationsystem.exceptions;

import java.time.LocalDateTime;

public class AppointmentDateNotAvailableException extends Exception {
    private final static String MESSAGE = "Appointment not found for date ";

    public AppointmentDateNotAvailableException(LocalDateTime appointmentDateTime) {
        super(MESSAGE + appointmentDateTime);
    }

    public AppointmentDateNotAvailableException(String message) {
        super(message);
    }
}
