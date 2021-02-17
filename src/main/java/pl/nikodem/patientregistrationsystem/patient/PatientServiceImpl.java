package pl.nikodem.patientregistrationsystem.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = patientRepository.findByUsername(username);
        if (user != null) return user;
        else throw new UsernameNotFoundException("Username " + username + " not found");
    }

    @Override
    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public Patient findUserByEmail(String email) {
        return patientRepository.findPatientByEmail(email);
    }

    @Override
    public void enablePatient(String email) {
        patientRepository.enablePatient(email);
    }
}
