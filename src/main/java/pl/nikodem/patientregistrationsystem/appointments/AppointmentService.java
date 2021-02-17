package pl.nikodem.patientregistrationsystem.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.doctor.MeetingInterval;
import pl.nikodem.patientregistrationsystem.exceptions.AppointmentTimeUnavailableException;
import pl.nikodem.patientregistrationsystem.patient.Patient;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final MeetingIntervalRepository meetingIntervalRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, MeetingIntervalRepository meetingIntervalRepository) {
        this.appointmentRepository = appointmentRepository;
        this.meetingIntervalRepository = meetingIntervalRepository;
    }

    @Transactional
    public Appointment addAppointmentAndDeleteMeetingInterval(Doctor doctor, Patient patient, MeetingInterval meetingInterval) throws AppointmentTimeUnavailableException {
        if (doctor.hasAvailableMeetingInterval(meetingInterval)) {
            Appointment appointment = new Appointment(doctor, patient, LocalDateTime.of(meetingInterval.getDate(), meetingInterval.getStartTime()));
            appointmentRepository.save(appointment);
            meetingIntervalRepository.deleteByStartTimeAndDoctor(meetingInterval.getStartTime(), doctor);
            return appointment;
        }
        else throw new AppointmentTimeUnavailableException();
    }

    public boolean deleteAppointmentById(long id) {
        if (appointmentRepository.findById(id).isPresent()) {
            appointmentRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }

    public Optional<Appointment> findById(long id) {
        return appointmentRepository.findById(id);
    }
}
