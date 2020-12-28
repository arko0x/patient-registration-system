package pl.nikodem.patientregistrationsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.nikodem.patientregistrationsystem.repository.DoctorRepository;

@Service
public class DoctorDetailsService implements UserDetailsService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorDetailsService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return doctorRepository.findByUsername(username);
    }
}
