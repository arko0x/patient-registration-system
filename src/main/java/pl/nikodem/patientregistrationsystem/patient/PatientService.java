package pl.nikodem.patientregistrationsystem.patient;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface PatientService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void savePatient(Patient patient);

    Patient findUserByEmail(String email);

    void enablePatient(String email);
}
