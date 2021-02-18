package pl.nikodem.patientregistrationsystem.doctor.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.nikodem.patientregistrationsystem.exceptions.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/doctor")
public class DoctorRegistrationController {
    private final DoctorRegistrationService doctorRegistrationService;

    @Autowired
    public DoctorRegistrationController(DoctorRegistrationService doctorRegistrationService) {
        this.doctorRegistrationService = doctorRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> proceedRegistrationData(@Valid @RequestBody DoctorRegistrationDTO doctor, Errors errors) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors().toString(), HttpStatus.NOT_ACCEPTABLE);
        }

        doctorRegistrationService.register(doctor);
        return new ResponseEntity<>("User " + doctor.getUsername() + " properly created!", HttpStatus.OK);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<String> confirmToken(@RequestParam String token) throws EmailAlreadyConfirmedException, TokenNotFoundException, TokenExpiredException {
        String message = doctorRegistrationService.confirmToken(token);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
