package pl.nikodem.patientregistrationsystem.patient.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.nikodem.patientregistrationsystem.exceptions.*;
import pl.nikodem.patientregistrationsystem.patient.Patient;
import pl.nikodem.patientregistrationsystem.patient.PatientService;
import pl.nikodem.patientregistrationsystem.email.EmailSender;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PatientRegistrationServiceTest {
    @Mock
    private PatientService patientService;

    @Mock
    private PatientConfirmationTokenService patientConfirmationTokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private PatientRegistrationServiceImpl patientRegistrationService;

    private PatientRegistrationDTO testPatient;

    @BeforeEach
    public void initialize() {
        testPatient = new PatientRegistrationDTO();
        testPatient.setUsername("TestUser");
        testPatient.setFirstName("Test");
        testPatient.setLastName("User");
        testPatient.setEmail("test@mail.com");
        testPatient.setRole("ROLE_PATIENT");
        testPatient.setPassword("password");
        testPatient.setBirthDate(LocalDate.now().minusYears(20));
        testPatient.setCreatedAt(Instant.now());
    }

    @Test
    public void givenRegisterMethodWhenRegisterUserIsNewThenRegisterUserCorrectly() throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        when(patientService.loadUserByUsername(any(String.class))).thenThrow(UsernameNotFoundException.class);
        assertDoesNotThrow(() -> patientRegistrationService.register(testPatient));
    }

    @Test
    public void givenRegisterMethodWhenRegisterUsernameAlreadyExistsThenThrowAnUsernameAlreadyExistsException() {
        when(patientService.loadUserByUsername(any(String.class))).thenReturn(new Patient());
        assertThrows(UsernameAlreadyExistsException.class, () -> patientRegistrationService.register(testPatient));
    }

    @Test
    public void givenRegisterMethodWhenRegisterEmailAlreadyExistsThenThrowAnEmailAlreadyExistsException() {
        when(patientService.findUserByEmail(any(String.class))).thenReturn(new Patient());
        when(patientService.loadUserByUsername(any(String.class))).thenThrow(UsernameNotFoundException.class);
        assertThrows(EmailAlreadyExistsException.class, () -> patientRegistrationService.register(testPatient));
    }

    @Test
    public void givenConfirmTokenMethodWhenPatientConfirmationTokenIsValidThenConfirmTokenCorrectly() throws EmailAlreadyConfirmedException, TokenNotFoundException, TokenExpiredException {
        PatientConfirmationToken token = new PatientConfirmationToken("TOKEN", LocalDateTime.now().minusMinutes(10), LocalDateTime.now().plusMinutes(10), new Patient());
        when(patientConfirmationTokenService.getToken(any(String.class))).thenReturn(Optional.of(token));
        assertEquals("Confirmed", patientRegistrationService.confirmToken("TOKEN"));
    }

    @Test
    public void givenConfirmTokenMethodWhenPatientConfirmationTokenNotExistsThenThrowTokenNotFoundException() {
        when(patientConfirmationTokenService.getToken(any(String.class))).thenReturn(Optional.empty());
        assertThrows(TokenNotFoundException.class, () -> patientRegistrationService.confirmToken("TOKEN"));
    }

    @Test
    public void givenConfirmTokenMethodWhenPatientConfirmationTokenIsAlreadyConfirmedThenThrowAnEmailAlreadyConfirmedException() {
        PatientConfirmationToken token = new PatientConfirmationToken("TOKEN", LocalDateTime.now().minusMinutes(10), LocalDateTime.now().plusMinutes(10), new Patient());
        token.setConfirmedAt(LocalDateTime.now());
        when(patientConfirmationTokenService.getToken(any(String.class))).thenReturn(Optional.of(token));
        assertThrows(EmailAlreadyConfirmedException.class, () -> patientRegistrationService.confirmToken("TOKEN"));
    }

    @Test
    public void givenConfirmTokenMethodWhenTokenExpiredThenThrowTokenExpiredException() {
        PatientConfirmationToken token = new PatientConfirmationToken("TOKEN", LocalDateTime.now().minusMinutes(10), LocalDateTime.now().minusMinutes(5), new Patient());
        when(patientConfirmationTokenService.getToken(any(String.class))).thenReturn(Optional.of(token));
        assertThrows(TokenExpiredException.class, () -> patientRegistrationService.confirmToken("TOKEN"));
    }
}
