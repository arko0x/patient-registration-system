package pl.nikodem.patientregistrationsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.nikodem.patientregistrationsystem.entity.Specialization;

public interface SpecializationRepository extends CrudRepository<Specialization, Integer> {
}
