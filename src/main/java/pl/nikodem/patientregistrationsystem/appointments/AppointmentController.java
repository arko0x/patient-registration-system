package pl.nikodem.patientregistrationsystem.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.nikodem.patientregistrationsystem.doctor.MeetingInterval;
import pl.nikodem.patientregistrationsystem.exceptions.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AppointmentController {
    private final MeetingIntervalService meetingIntervalService;

    @Autowired
    public AppointmentController(MeetingIntervalService meetingIntervalService) {
        this.meetingIntervalService = meetingIntervalService;
    }

    @GetMapping(params = {"page", "size", "date"}, path = "/patient/availablemeetings")
    public List<MeetingInterval> getAllAvailableMeetingIntervals(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                 @RequestParam("page") int page,
                                                                 @RequestParam("size") int size,
                                                                 @RequestParam(name = "sort", required = false, defaultValue = "startTime") List<String> sortBy,
                                                                 @RequestParam(name = "desc", required = false, defaultValue = "false") boolean desc) throws ResourceNotFoundException {
        Page<MeetingInterval> resultPage = meetingIntervalService.getAllAvailableMeetingsInterval(date, page, size, Sort.by(sortBy.stream().map(!desc ? Sort.Order::asc : Sort.Order::desc).collect(Collectors.toList())));
        if (page >= resultPage.getTotalPages()) throw new ResourceNotFoundException("Page not found exception");

        return resultPage.getContent();
    }
}
