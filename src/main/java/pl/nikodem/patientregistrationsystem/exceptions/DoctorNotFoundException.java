package pl.nikodem.patientregistrationsystem.exceptions;


public class DoctorNotFoundException extends Exception {
    public DoctorNotFoundException() {
    }

    public DoctorNotFoundException(String message) {
        super(message);
    }
}
