package pl.nikodem.patientregistrationsystem.doctor.registration;

import java.util.Optional;

public interface DoctorConfirmationTokenService {
    void saveConfirmationToken(DoctorConfirmationToken confirmationToken);

    Optional<DoctorConfirmationToken> getToken(String token);

    void setConfirmedAt(String token);
}
