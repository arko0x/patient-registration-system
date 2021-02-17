package pl.nikodem.patientregistrationsystem.helper;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.nikodem.patientregistrationsystem.doctor.*;
import pl.nikodem.patientregistrationsystem.appointments.*;
import pl.nikodem.patientregistrationsystem.patient.Patient;
import pl.nikodem.patientregistrationsystem.appointments.AppointmentRepository;
import pl.nikodem.patientregistrationsystem.patient.PatientRepository;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DataLoader implements ApplicationRunner {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final SpecializationRepository specializationRepository;
    private final AppointmentRepository appointmentRepository;
    private final MeetingIntervalRepository meetingIntervalRepository;
    private final PasswordEncoder passwordEncoder;

    private final List<String> names = List.of("Jan", "Marek", "Bartosz", "Kamil", "Patryk", "Zdzislaw", "Eugeniusz", "Robert", "Jakub");
    private final List<String> lastNames = List.of("Kowalski", "Nowak", "Zuraw", "Kruk", "Budka", "Trutka", "Parobek", "Niedzielan", "Lewandowski");
    private final List<Integer> birthYears = IntStream.range(1950, 1991).boxed().collect(Collectors.toList());
    private final List<Integer> months = IntStream.range(1, 13).boxed().collect(Collectors.toList());
    private final List<Integer> daysOfMonth = IntStream.range(1, 28).boxed().collect(Collectors.toList());

    private List<Specialization> specializations = Arrays.stream(SpecializationType.values()).map(Specialization::new).collect(Collectors.toList());
    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();

    @Autowired
    public DataLoader(DoctorRepository doctorRepository, PatientRepository patientRepository, SpecializationRepository specializationRepository, AppointmentRepository appointmentRepository, MeetingIntervalRepository meetingIntervalRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.specializationRepository = specializationRepository;
        this.meetingIntervalRepository = meetingIntervalRepository;
        this.passwordEncoder = passwordEncoder;
        this.appointmentRepository = appointmentRepository;
    }

    private void addSpecializations() {
        specializationRepository.saveAll(specializations);
    }

    private void addSomeDoctors() {
        for (int i = 0; i < 9; i++) {
            String name = names.get(new Random().nextInt(names.size()));
            String lastName = lastNames.get(new Random().nextInt(lastNames.size()));
            Doctor doctor = new Doctor(name + lastName + i,
                    name.toLowerCase() + lastName.toLowerCase() + i + "@gmail.com",
                    passwordEncoder.encode(lastName + i),
                    "ROLE_DOCTOR",
                    Instant.now(),
                    name,
                    lastName,
                    LocalDate.of(birthYears.get(new Random().nextInt(birthYears.size())),
                            months.get(new Random().nextInt(months.size())),
                            daysOfMonth.get(new Random().nextInt(daysOfMonth.size()))),
                    List.of(specializations.get(new Random().nextInt(specializations.size())))
            );

            doctor.setEnabled(true);
            doctors.add(doctor);
        }

        doctorRepository.saveAll(doctors);
    }

    private void addSomePatients() {
        for (int i = 0; i < 9; i++) {
            String name = names.get(new Random().nextInt(names.size()));
            String lastName = lastNames.get(new Random().nextInt(lastNames.size()));
            Patient patient = new Patient(name + lastName + i + 20,
                    passwordEncoder.encode(lastName + i + 20),
                    name.toLowerCase() + lastName.toLowerCase() + (i + 20) + "@gmail.com",
                    "ROLE_PATIENT",
                    Instant.now(),
                    name,
                    lastName,
                    LocalDate.of(birthYears.get(new Random().nextInt(birthYears.size())),
                            months.get(new Random().nextInt(months.size())),
                            daysOfMonth.get(new Random().nextInt(daysOfMonth.size())))
                    );

            patient.setEnabled(true);
            patients.add(patient);
        }

        patientRepository.saveAll(patients);
    }

    private void addSomeAppointments() {
        List<LocalDate> dates = List.of(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), LocalDate.now().plusDays(3));
        List<LocalTime> hours = Lists.newArrayList(LocalTime.of(10, 0), LocalTime.of(10, 30), LocalTime.of(11, 0),
                LocalTime.of(11, 30), LocalTime.of(12, 0), LocalTime.of(12, 30), LocalTime.of(13, 0),
                LocalTime.of(13, 30), LocalTime.of(14, 0), LocalTime.of(14, 30), LocalTime.of(15, 0),
                LocalTime.of(15, 30), LocalTime.of(16, 0), LocalTime.of(16, 30), LocalTime.of(17, 0));

        for (int i = 0; i < 15; i++) {
            Doctor doctor = doctors.get(new Random().nextInt(doctors.size()));
            Patient patient = patients.get(new Random().nextInt(patients.size()));
            LocalDate date = dates.get(new Random().nextInt(dates.size()));
            LocalTime time = hours.get(new Random().nextInt(hours.size()));
            LocalDateTime dateTime = LocalDateTime.of(date, time);

            hours.remove(time);

            Appointment appointment = new Appointment(doctor, patient, dateTime);

            appointmentRepository.save(appointment);
        }
    }

    private void addSomeMeetingIntervals() {
        final int FOUR_DAYS_FROM_TODAY = 4;
        final int ONE_WEEK = 7;
        final int HALF_AN_HOUR = 30;

        for (LocalDate date = LocalDate.now().plusDays(FOUR_DAYS_FROM_TODAY); date.isBefore(LocalDate.now().plusDays(FOUR_DAYS_FROM_TODAY + ONE_WEEK)); date = date.plusDays(1)) {
            for (LocalTime time = LocalTime.of(8, 0); time.isBefore(LocalTime.of(16, 0)); time = time.plusMinutes(HALF_AN_HOUR)) {
                for (Doctor doctor : doctors) {
                    MeetingInterval meetingInterval = new MeetingInterval(date, time, time.plusMinutes(HALF_AN_HOUR), doctor);
                    meetingIntervalRepository.save(meetingInterval);
                }
            }
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        addSpecializations();
        addSomeDoctors();
        addSomePatients();
        addSomeAppointments();
        addSomeMeetingIntervals();
    }
}
