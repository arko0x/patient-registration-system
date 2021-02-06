package pl.nikodem.patientregistrationsystem.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.nikodem.patientregistrationsystem.exceptions.EmailAlreadyExistsException;
import pl.nikodem.patientregistrationsystem.exceptions.UsernameAlreadyExistsException;
import pl.nikodem.patientregistrationsystem.registration.PatientRegistrationDTO;

import java.time.Instant;

@Service
@AllArgsConstructor
public class PatientRegistrationService {
    private final PatientService patientService;
    private final PasswordEncoder passwordEncoder;

    private boolean canPatientBeRegistered(PatientRegistrationDTO patient) throws UsernameAlreadyExistsException, EmailAlreadyExistsException{
        if (patientService.loadUserByUsername(patient.getUsername()) != null)
            throw new UsernameAlreadyExistsException();
        else if (patientService.findUserByEmail(patient.getEmail()) != null)
            throw new EmailAlreadyExistsException();
        else
            return true;
    }

    public void register(PatientRegistrationDTO patient) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        if (canPatientBeRegistered(patient)) {
            patient.setCreatedAt(Instant.now());
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));
            patientService.savePatient(patient.toPatient());
        }
    }
}
