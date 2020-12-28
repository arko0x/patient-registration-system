package pl.nikodem.patientregistrationsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.nikodem.patientregistrationsystem.repository.PatientRepository;

@Service
public class PatientDetailsService implements UserDetailsService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientDetailsService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return patientRepository.findByUsername(username);
    }
}
