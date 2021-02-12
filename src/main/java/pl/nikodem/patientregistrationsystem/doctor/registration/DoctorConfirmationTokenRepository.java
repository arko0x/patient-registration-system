package pl.nikodem.patientregistrationsystem.doctor.registration;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DoctorConfirmationTokenRepository extends CrudRepository<DoctorConfirmationToken, Long> {
    Optional<DoctorConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE DoctorConfirmationToken c SET c.confirmedAt = ?2 WHERE c.token = ?1")
    void updateConfirmedAt(String token, LocalDateTime time);
}
