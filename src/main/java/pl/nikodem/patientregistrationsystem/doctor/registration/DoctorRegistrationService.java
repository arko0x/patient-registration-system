package pl.nikodem.patientregistrationsystem.doctor.registration;

import pl.nikodem.patientregistrationsystem.exceptions.*;

public interface DoctorRegistrationService {
    void register(DoctorRegistrationDTO doctor) throws EmailAlreadyExistsException, UsernameAlreadyExistsException;

    String confirmToken(String token) throws TokenNotFoundException, EmailAlreadyConfirmedException, TokenExpiredException;
}
