package pl.nikodem.patientregistrationsystem.email;

public interface EmailSender {
    void send(String to, String email);
}
