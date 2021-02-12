package pl.nikodem.patientregistrationsystem.doctor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nikodem.patientregistrationsystem.appointments.Appointment;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.doctor.DoctorService;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/info")
    public Doctor getCurrentUserInfo(Principal principal) {
        return (Doctor) doctorService.loadUserByUsername(principal.getName());
    }

    @GetMapping("/appointments")
    public List<Appointment> getCurrentUserAppointments(Principal principal) {
        Doctor doctor = (Doctor) doctorService.loadUserByUsername(principal.getName());
        return doctor.getAppointments();
    }
}
