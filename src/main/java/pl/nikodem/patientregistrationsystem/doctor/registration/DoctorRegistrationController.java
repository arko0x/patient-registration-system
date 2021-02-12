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
    public ResponseEntity<String> proceedRegistrationData(@Valid @RequestBody DoctorRegistrationDTO doctor, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors().toString(), HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            doctorRegistrationService.register(doctor);
            return new ResponseEntity<>("User " + doctor.getUsername() + " properly created!", HttpStatus.OK);
        }
        catch (UsernameAlreadyExistsException usernameAlreadyExistsException) {
            return new ResponseEntity<>("User " + doctor.getUsername() + " already exists!", HttpStatus.CONFLICT);
        }
        catch (EmailAlreadyExistsException emailAlreadyExistsException) {
            return new ResponseEntity<>("Email " + doctor.getEmail() + " already exists!", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<String> confirmToken(@RequestParam String token) {
        String message;
        try {
            message = doctorRegistrationService.confirmToken(token);
        } catch (TokenNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (EmailAlreadyConfirmedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (TokenExpiredException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
