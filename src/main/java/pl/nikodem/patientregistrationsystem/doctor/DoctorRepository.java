package pl.nikodem.patientregistrationsystem.doctor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Doctor findByUsername(String username);

    Doctor findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Doctor d SET d.enabled = true")
    void enableDoctor(String email);
}
