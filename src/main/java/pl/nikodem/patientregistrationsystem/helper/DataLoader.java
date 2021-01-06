package pl.nikodem.patientregistrationsystem.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.nikodem.patientregistrationsystem.entity.Doctor;
import pl.nikodem.patientregistrationsystem.entity.Patient;
import pl.nikodem.patientregistrationsystem.repository.DoctorRepository;
import pl.nikodem.patientregistrationsystem.repository.PatientRepository;

import java.time.Instant;
import java.time.LocalDate;

@Component
public class DataLoader implements ApplicationRunner {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(DoctorRepository doctorRepository, PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        Doctor doctor = new Doctor(1, "jankowalski123", passwordEncoder.encode("password"), "ROLE_DOCTOR", Instant.now());
        doctor.setBirthDate(LocalDate.of(1980, 3, 22));
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowalski");
        doctorRepository.save(doctor);
        patientRepository.save(new Patient(1, "pawelnowak11", passwordEncoder.encode("haslo"), "ROLE_PATIENT", Instant.now()));
    }
}
