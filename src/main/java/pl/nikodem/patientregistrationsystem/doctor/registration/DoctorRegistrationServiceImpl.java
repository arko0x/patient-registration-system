package pl.nikodem.patientregistrationsystem.doctor.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.doctor.DoctorService;
import pl.nikodem.patientregistrationsystem.email.EmailSender;
import pl.nikodem.patientregistrationsystem.exceptions.*;
import pl.nikodem.patientregistrationsystem.email.ConfirmationEmailGenerator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DoctorRegistrationServiceImpl implements DoctorRegistrationService {
    private final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;
    private final DoctorConfirmationTokenService doctorConfirmationTokenService;
    private final EmailSender emailSender;

    @Autowired
    public DoctorRegistrationServiceImpl(DoctorService doctorService,
                                         PasswordEncoder passwordEncoder,
                                         DoctorConfirmationTokenService doctorConfirmationTokenService,
                                         EmailSender emailSender) {
        this.doctorService = doctorService;
        this.passwordEncoder = passwordEncoder;
        this.doctorConfirmationTokenService = doctorConfirmationTokenService;
        this.emailSender = emailSender;
    }

    private boolean canDoctorBeRegistered(DoctorRegistrationDTO doctor) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        try {
            doctorService.loadUserByUsername(doctor.getUsername());
            throw new UsernameAlreadyExistsException();
        }
        catch (UsernameNotFoundException ignored) {}

        if (doctorService.findUserByEmail(doctor.getEmail()) != null) throw new EmailAlreadyExistsException();
        else return true;
    }

    public void register(DoctorRegistrationDTO doctor) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        if (canDoctorBeRegistered(doctor)) {
            doctor.setCreatedAt(Instant.now());
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));

            Doctor doctorToSave = doctor.toDoctor();
            doctorService.saveDoctor(doctorToSave);

            String token = UUID.randomUUID().toString();
            DoctorConfirmationToken confirmationToken = new DoctorConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    doctorToSave
            );

            doctorConfirmationTokenService.saveConfirmationToken(confirmationToken);

            emailSender.send(doctorToSave.getEmail(), ConfirmationEmailGenerator.generateEmailMessageForDoctor(doctor.getUsername(), token));

        }
    }

    @Transactional
    public String confirmToken(String token) throws TokenNotFoundException, EmailAlreadyConfirmedException, TokenExpiredException {
        DoctorConfirmationToken confirmationToken = doctorConfirmationTokenService
                .getToken(token)
                .orElseThrow(TokenNotFoundException::new);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException();
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException(expiredAt);
        }

        doctorConfirmationTokenService.setConfirmedAt(token);
        doctorService.enableDoctor(confirmationToken.getDoctor().getEmail());
        return "Confirmed";
    }
}
