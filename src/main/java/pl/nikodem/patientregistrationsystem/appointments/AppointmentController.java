package pl.nikodem.patientregistrationsystem.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.doctor.DoctorService;
import pl.nikodem.patientregistrationsystem.doctor.MeetingInterval;
import pl.nikodem.patientregistrationsystem.exceptions.AppointmentTimeUnavailableException;
import pl.nikodem.patientregistrationsystem.exceptions.DoctorNotFoundException;
import pl.nikodem.patientregistrationsystem.exceptions.ResourceNotFoundException;
import pl.nikodem.patientregistrationsystem.patient.Patient;
import pl.nikodem.patientregistrationsystem.patient.PatientService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AppointmentController {
    private final MeetingIntervalService meetingIntervalService;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public AppointmentController(MeetingIntervalService meetingIntervalService, AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
        this.meetingIntervalService = meetingIntervalService;
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
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

    @PostMapping("patient/createappointment")
    public ResponseEntity<Appointment> createAppointment(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                         @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                                         @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
                                                         Principal principal,
                                                         @RequestParam("doctor_id") long doctorId) {
        Doctor doctor;
        try {
            doctor = doctorService.findById(doctorId);
        } catch (DoctorNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Patient patient = (Patient) patientService.loadUserByUsername(principal.getName());

        MeetingInterval meetingInterval = new MeetingInterval(date, startTime, endTime, doctor);

        try {
            Appointment appointment = appointmentService.addAppointmentAndDeleteMeetingInterval(doctor, patient, meetingInterval);
            return new ResponseEntity<>(appointment, HttpStatus.CREATED);
        } catch (AppointmentTimeUnavailableException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/doctor/deleteappointment")
    public ResponseEntity<String> proceedDeletingAppointment(@RequestParam(name = "appointment_id") long id, Principal principal) {
        if (appointmentService.findById(id).isPresent() && !appointmentService.findById(id).get().getDoctor().getUsername().equals(principal.getName())) {
            return new ResponseEntity<>("Operation forbidden", HttpStatus.FORBIDDEN);
        }

        if (appointmentService.deleteAppointmentById(id)) {
            return new ResponseEntity<>("Correctly deleted appointment", HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Appointment not exists", HttpStatus.NOT_FOUND);
        }
    }
}
