package pl.nikodem.patientregistrationsystem.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.nikodem.patientregistrationsystem.exceptions.DoctorNotFoundException;

import java.util.Optional;

@Service
public class DoctorService implements UserDetailsService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = doctorRepository.findByUsername(username);
        if (user != null) return user;
        else throw new UsernameNotFoundException("Username " + username + " not found");
    }

    public Doctor findUserByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    public void saveDoctor(Doctor doctorToSave) {
        doctorRepository.save(doctorToSave);
    }

    public void enableDoctor(String email) {
        doctorRepository.enableDoctor(email);
    }

    public Doctor findById(long doctorId) throws DoctorNotFoundException {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) return doctorOptional.get();
        else throw new DoctorNotFoundException("Doctor with id " + doctorId + " not found");
    }
}
