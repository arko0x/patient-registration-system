package pl.nikodem.patientregistrationsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.nikodem.patientregistrationsystem.entity.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
