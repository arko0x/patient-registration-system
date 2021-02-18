package pl.nikodem.patientregistrationsystem.patient.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.nikodem.patientregistrationsystem.exceptions.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientRegistrationController {
    private final PatientRegistrationService patientRegistrationService;

    @Autowired
    public PatientRegistrationController(PatientRegistrationService patientRegistrationService) {
        this.patientRegistrationService = patientRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> proceedRegistrationData(@Valid @RequestBody PatientRegistrationDTO patient, Errors errors) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors().toString(), HttpStatus.NOT_ACCEPTABLE);
        }

        patientRegistrationService.register(patient);
        return new ResponseEntity<>("User " + patient.getUsername() + " properly created!", HttpStatus.OK);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<String> confirmToken(@RequestParam String token) throws EmailAlreadyConfirmedException, TokenNotFoundException, TokenExpiredException {
        String message = patientRegistrationService.confirmToken(token);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
