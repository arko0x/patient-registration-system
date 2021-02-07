package pl.nikodem.patientregistrationsystem.patient.registration.email;

public class ConfirmationEmailGenerator {
    public static String generateEmailMessage(String username, String token) {
        return "<div>Confirm your email, " + username + "!</div><br>" +
                "<div><a href = \"localhost:8080/patient/register/confirm?token=" + token + "\">Confirmation link </a></div>";
    }
}
