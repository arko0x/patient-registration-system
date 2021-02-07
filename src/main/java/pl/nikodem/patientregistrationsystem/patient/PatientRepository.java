package pl.nikodem.patientregistrationsystem.patient;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@org.springframework.transaction.annotation.Transactional(readOnly = true)
public interface PatientRepository extends CrudRepository<Patient, Long> {
    Patient findByUsername(String username);
    Patient findPatientByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Patient patient SET patient.enabled = TRUE WHERE patient.email = ?1")
    void enablePatient(String email);
}
