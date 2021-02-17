package pl.nikodem.patientregistrationsystem.doctor.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DoctorConfirmationTokenServiceImpl implements DoctorConfirmationTokenService {

    private final DoctorConfirmationTokenRepository doctorConfirmationTokenRepository;

    @Autowired
    public DoctorConfirmationTokenServiceImpl(DoctorConfirmationTokenRepository doctorConfirmationTokenRepository) {
        this.doctorConfirmationTokenRepository = doctorConfirmationTokenRepository;
    }

    public void saveConfirmationToken(DoctorConfirmationToken confirmationToken) {
        doctorConfirmationTokenRepository.save(confirmationToken);
    }

    public Optional<DoctorConfirmationToken> getToken(String token) {
        return doctorConfirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        doctorConfirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
