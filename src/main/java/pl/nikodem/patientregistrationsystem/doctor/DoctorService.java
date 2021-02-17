package pl.nikodem.patientregistrationsystem.doctor;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.nikodem.patientregistrationsystem.exceptions.DoctorNotFoundException;

public interface DoctorService extends UserDetailsService {
    Doctor findUserByEmail(String email);

    void saveDoctor(Doctor doctorToSave);

    void enableDoctor(String email);

    Doctor findById(long doctorId) throws DoctorNotFoundException;
}
