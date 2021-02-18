package pl.nikodem.patientregistrationsystem.patient.registration;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nikodem.patientregistrationsystem.exceptions.*;
import pl.nikodem.patientregistrationsystem.patient.Patient;
import pl.nikodem.patientregistrationsystem.patient.PatientService;
import pl.nikodem.patientregistrationsystem.email.ConfirmationEmailGenerator;
import pl.nikodem.patientregistrationsystem.email.EmailSender;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PatientRegistrationServiceImpl implements PatientRegistrationService {
    private final PatientService patientService;
    private final PatientConfirmationTokenService patientConfirmationTokenService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    private boolean canPatientBeRegistered(PatientRegistrationDTO patient) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        try {
            patientService.loadUserByUsername(patient.getUsername());
            throw new UsernameAlreadyExistsException();
        }
        catch (UsernameNotFoundException ignored) {}

        if (patientService.findUserByEmail(patient.getEmail()) != null) throw new EmailAlreadyExistsException();
        else return true;
    }

    public void register(PatientRegistrationDTO patient) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        if (canPatientBeRegistered(patient)) {
            patient.setCreatedAt(Instant.now());
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));

            Patient patientToSave = patient.toPatient();
            patientService.savePatient(patientToSave);

            String token = UUID.randomUUID().toString();
            PatientConfirmationToken confirmationToken = new PatientConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    patientToSave
            );

            patientConfirmationTokenService.saveConfirmationToken(confirmationToken);

            emailSender.send(patientToSave.getEmail(), ConfirmationEmailGenerator.generateEmailMessageForPatient(patient.getUsername(), token));

        }
    }

    @Transactional
    public String confirmToken(String token) throws TokenNotFoundException, EmailAlreadyConfirmedException, TokenExpiredException {
        PatientConfirmationToken confirmationToken = patientConfirmationTokenService
                .getToken(token)
                .orElseThrow(TokenNotFoundException::new);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException();
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException(expiredAt);
        }

        patientConfirmationTokenService.setConfirmedAt(token);
        patientService.enablePatient(confirmationToken.getPatient().getEmail());
        return "Confirmed";
    }
}