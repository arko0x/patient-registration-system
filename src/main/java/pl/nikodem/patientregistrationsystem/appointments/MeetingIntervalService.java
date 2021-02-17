package pl.nikodem.patientregistrationsystem.appointments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import pl.nikodem.patientregistrationsystem.doctor.MeetingInterval;

import java.time.LocalDate;

public interface MeetingIntervalService {
    Page<MeetingInterval> getAllAvailableMeetingsInterval(LocalDate date, int page, int size, Sort sort);
}
