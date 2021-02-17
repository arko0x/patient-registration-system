package pl.nikodem.patientregistrationsystem.appointments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.doctor.MeetingInterval;

import java.time.LocalDate;
import java.time.LocalTime;

public interface MeetingIntervalRepository extends CrudRepository<MeetingInterval, Long> {
    Page<MeetingInterval> findByDate(LocalDate date, Pageable pageable);
    void deleteByStartTimeAndDoctor(LocalTime startTime, Doctor doctor);
}
