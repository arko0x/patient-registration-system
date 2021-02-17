package pl.nikodem.patientregistrationsystem.appointments;

import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.doctor.MeetingInterval;
import pl.nikodem.patientregistrationsystem.exceptions.AppointmentTimeUnavailableException;
import pl.nikodem.patientregistrationsystem.patient.Patient;

import java.util.Optional;

public interface AppointmentService {
    Appointment addAppointmentAndDeleteMeetingInterval(Doctor doctor, Patient patient, MeetingInterval meetingInterval) throws AppointmentTimeUnavailableException;

    boolean deleteAppointmentById(long id);

    Optional<Appointment> findById(long id);
}
