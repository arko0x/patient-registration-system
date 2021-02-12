package pl.nikodem.patientregistrationsystem.appointments;

import org.springframework.data.repository.CrudRepository;
import pl.nikodem.patientregistrationsystem.appointments.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
