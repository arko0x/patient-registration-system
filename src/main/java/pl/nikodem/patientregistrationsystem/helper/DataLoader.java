package pl.nikodem.patientregistrationsystem.helper;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.doctor.Specialization;
import pl.nikodem.patientregistrationsystem.doctor.SpecializationType;
import pl.nikodem.patientregistrationsystem.appointments.*;
import pl.nikodem.patientregistrationsystem.patient.Patient;
import pl.nikodem.patientregistrationsystem.appointments.AppointmentRepository;
import pl.nikodem.patientregistrationsystem.doctor.DoctorRepository;
import pl.nikodem.patientregistrationsystem.patient.PatientRepository;
import pl.nikodem.patientregistrationsystem.doctor.SpecializationRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataLoader implements ApplicationRunner {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final SpecializationRepository specializationRepository;
    private final AppointmentRepository appointmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(DoctorRepository doctorRepository, PatientRepository patientRepository, SpecializationRepository specializationRepository, AppointmentRepository appointmentRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.specializationRepository = specializationRepository;
        this.passwordEncoder = passwordEncoder;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Doctor doctor = new Doctor(1, "jankowalski123", passwordEncoder.encode("password"), "ROLE_DOCTOR", Instant.now());
        doctor.setBirthDate(LocalDate.of(1980, 3, 22));
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowalski");

        List<Specialization> specializations = Arrays.stream(SpecializationType.values()).map(Specialization::new).collect(Collectors.toList());

        doctor.setSpecializations(Lists.newArrayList(specializations.get(2), specializations.get(4)));

        specializationRepository.saveAll(specializations);
        doctorRepository.save(doctor);

        Patient patient = new Patient("pawelnowak11", passwordEncoder.encode("haslo"), "pawel123@gmail.com", "ROLE_PATIENT", Instant.now(),
                "Paweł", "Nowak", LocalDate.of(1980, Month.APRIL, 1));
        patientRepository.save(patient);

        Appointment appointment = new Appointment(1, doctor, patient, Instant.parse("2021-12-03T10:15:30.00Z"));
        appointmentRepository.save(appointment);
        patient.addAppointment(appointment, doctor);
    }
}
