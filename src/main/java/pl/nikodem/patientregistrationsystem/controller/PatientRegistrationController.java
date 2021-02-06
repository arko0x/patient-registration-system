package pl.nikodem.patientregistrationsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nikodem.patientregistrationsystem.exceptions.EmailAlreadyExistsException;
import pl.nikodem.patientregistrationsystem.exceptions.UsernameAlreadyExistsException;
import pl.nikodem.patientregistrationsystem.registration.PatientRegistrationDTO;
import pl.nikodem.patientregistrationsystem.service.PatientRegistrationService;

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
    public ResponseEntity<String> proceedRegistrationData(@Valid @RequestBody PatientRegistrationDTO patient, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors().toString(), HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            patientRegistrationService.register(patient);
            return new ResponseEntity<>("User " + patient.getUsername() + " properly created!", HttpStatus.OK);
        }
        catch (UsernameAlreadyExistsException usernameAlreadyExistsException) {
            return new ResponseEntity<>("User " + patient.getUsername() + " already exists!", HttpStatus.CONFLICT);
        }
        catch (EmailAlreadyExistsException emailAlreadyExistsException) {
            return new ResponseEntity<>("Email " + patient.getEmail() + " already exists!", HttpStatus.CONFLICT);
        }
    }

}
