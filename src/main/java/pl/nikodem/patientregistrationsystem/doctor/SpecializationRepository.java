package pl.nikodem.patientregistrationsystem.doctor;

import org.springframework.data.repository.CrudRepository;
import pl.nikodem.patientregistrationsystem.doctor.Specialization;

public interface SpecializationRepository extends CrudRepository<Specialization, Integer> {
}
