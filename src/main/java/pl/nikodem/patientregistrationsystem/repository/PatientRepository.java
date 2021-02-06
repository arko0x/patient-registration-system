package pl.nikodem.patientregistrationsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.nikodem.patientregistrationsystem.entity.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    Patient findByUsername(String username);
    Patient findPatientByEmail(String email);
}
