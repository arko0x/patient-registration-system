package pl.nikodem.patientregistrationsystem.email;

public class ConfirmationEmailGenerator {
    public static String generateEmailMessageForPatient(String username, String token) {
        return "<div>Confirm your email, " + username + "!</div><br>" +
                "<div><a href = \"localhost:8080/patient/register/confirm?token=" + token + "\">Confirmation link </a></div>";
    }

    public static String generateEmailMessageForDoctor(String username, String token) {
        return "<div>Confirm your email, " + username + "!</div><br>" +
                "<div><a href = \"localhost:8080/doctor/register/confirm?token=" + token + "\">Confirmation link </a></div>";
    }
}
