package pl.nikodem.patientregistrationsystem.patient.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientConfirmationTokenService {
    private final PatientConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(PatientConfirmationToken verificationToken) {
        confirmationTokenRepository.save(verificationToken);
    }

    public Optional<PatientConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
