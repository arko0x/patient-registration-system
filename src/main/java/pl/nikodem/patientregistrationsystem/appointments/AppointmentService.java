package pl.nikodem.patientregistrationsystem.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nikodem.patientregistrationsystem.appointments.Appointment;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.patient.Patient;
import pl.nikodem.patientregistrationsystem.exceptions.AppointmentDateNotAvailableException;
import pl.nikodem.patientregistrationsystem.appointments.AppointmentRepository;

import java.time.Instant;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void createAppointment(Doctor doctor, Patient patient, Instant date) throws AppointmentDateNotAvailableException{
        if (doctor.hasAvailableDate(date)) {
            Appointment appointment = new Appointment(doctor, patient, date);
            appointmentRepository.save(appointment);
            patient.addAppointment(appointment, doctor);
        }
        else {
            throw new AppointmentDateNotAvailableException();
        }
    }
}
