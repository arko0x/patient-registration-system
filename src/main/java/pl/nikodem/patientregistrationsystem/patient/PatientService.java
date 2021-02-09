package pl.nikodem.patientregistrationsystem.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PatientService implements UserDetailsService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = patientRepository.findByUsername(username);
        if (user != null) return user;
        else throw new UsernameNotFoundException("Username " + username + " not found");
    }

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient findUserByEmail(String email) {
        return patientRepository.findPatientByEmail(email);
    }

    public void enablePatient(String email) {
        patientRepository.enablePatient(email);
    }
}
