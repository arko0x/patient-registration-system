package pl.nikodem.patientregistrationsystem.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.nikodem.patientregistrationsystem.doctor.MeetingInterval;

import java.time.LocalDate;

@Service
public class MeetingIntervalServiceImpl implements MeetingIntervalService {
    private final MeetingIntervalRepository meetingIntervalRepository;

    @Autowired
    public MeetingIntervalServiceImpl(MeetingIntervalRepository meetingIntervalRepository) {
        this.meetingIntervalRepository = meetingIntervalRepository;
    }

    public Page<MeetingInterval> getAllAvailableMeetingsInterval(LocalDate date, int page, int size, Sort sort) {
        return meetingIntervalRepository.findByDate(date, PageRequest.of(page, size, sort));
    }
}
