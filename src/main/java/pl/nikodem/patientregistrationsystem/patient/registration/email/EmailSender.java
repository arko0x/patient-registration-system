package pl.nikodem.patientregistrationsystem.patient.registration.email;

public interface EmailSender {
    void send(String to, String email);
}
