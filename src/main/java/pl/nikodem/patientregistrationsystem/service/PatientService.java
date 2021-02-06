package pl.nikodem.patientregistrationsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.nikodem.patientregistrationsystem.entity.Doctor;
import pl.nikodem.patientregistrationsystem.entity.Patient;
import pl.nikodem.patientregistrationsystem.repository.PatientRepository;

@Service
public class PatientService implements UserDetailsService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return patientRepository.findByUsername(username);
    }

    public boolean savePatient(Patient patient) {
        patientRepository.save(patient);
        return true;
    }

    public Patient findUserByEmail(String email) {
        return patientRepository.findPatientByEmail(email);
    }
}
