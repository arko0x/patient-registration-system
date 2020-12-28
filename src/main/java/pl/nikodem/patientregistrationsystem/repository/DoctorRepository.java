package pl.nikodem.patientregistrationsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.nikodem.patientregistrationsystem.entity.Doctor;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Doctor findByUsername(String username);
}
