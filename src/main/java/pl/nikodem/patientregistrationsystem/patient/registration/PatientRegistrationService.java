package pl.nikodem.patientregistrationsystem.patient.registration;

import pl.nikodem.patientregistrationsystem.exceptions.*;

public interface PatientRegistrationService {
    void register(PatientRegistrationDTO patient) throws UsernameAlreadyExistsException, EmailAlreadyExistsException;

    String confirmToken(String token) throws TokenNotFoundException, EmailAlreadyConfirmedException, TokenExpiredException;
}
