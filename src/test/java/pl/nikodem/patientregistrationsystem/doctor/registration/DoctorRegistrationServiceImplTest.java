package pl.nikodem.patientregistrationsystem.doctor.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.doctor.DoctorService;
import pl.nikodem.patientregistrationsystem.email.EmailSender;
import pl.nikodem.patientregistrationsystem.exceptions.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorRegistrationServiceImplTest {
    @Mock
    private DoctorService doctorService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailSender emailSender;

    @Mock
    private DoctorConfirmationTokenService doctorConfirmationTokenService;

    @InjectMocks
    private DoctorRegistrationServiceImpl doctorRegistrationService;

    private DoctorRegistrationDTO testRegistrationDTO;

    @BeforeEach
    void setUp() {
        testRegistrationDTO = new DoctorRegistrationDTO();
        testRegistrationDTO.setUsername("Jan123");
        testRegistrationDTO.setFirstName("Jan");
        testRegistrationDTO.setLastName("Kowalski");
        testRegistrationDTO.setEmail("jkowalski@gmail.com");
        testRegistrationDTO.setBirthDate(LocalDate.now().minusYears(20));
        testRegistrationDTO.setCreatedAt(Instant.now());
        testRegistrationDTO.setPassword("password");
        testRegistrationDTO.setRole("ROLE_DOCTOR");
    }

    @Test
    public void givenRegisterMethodWhenUsernameAlreadyExistsThenThrowUsernameAlreadyExistsException() {
        when(doctorService.loadUserByUsername(any(String.class))).thenReturn(new Doctor());

        assertThrows(UsernameAlreadyExistsException.class, () -> doctorRegistrationService.register(testRegistrationDTO));
    }

    @Test
    public void givenRegisterMethodWhenEmailAlreadyExistsThenThrowEmailAlreadyExistsException() {
        when(doctorService.loadUserByUsername(anyString())).thenThrow(UsernameNotFoundException.class);
        when(doctorService.findUserByEmail(any(String.class))).thenReturn(new Doctor());

        assertThrows(EmailAlreadyExistsException.class, () -> doctorRegistrationService.register(testRegistrationDTO));
    }

    @Test
    public void givenRegisterMethodWhenEverythingIsOkayThenRegisterUserCorrectly() {
        when(doctorService.loadUserByUsername(any(String.class))).thenThrow(UsernameNotFoundException.class);

        assertDoesNotThrow(() -> doctorRegistrationService.register(testRegistrationDTO));
    }

    @Test
    public void givenConfirmTokenMethodWhenTokenIsExpiredThenThrowTokenExpiredException() {
        when(doctorConfirmationTokenService.getToken(anyString())).thenReturn(Optional.of(new DoctorConfirmationToken("token", LocalDateTime.now().minusMinutes(30), LocalDateTime.now().minusMinutes(20), testRegistrationDTO.toDoctor())));

        assertThrows(TokenExpiredException.class, () -> doctorRegistrationService.confirmToken("token"));
    }

    @Test
    public void givenConfirmTokenMethodWhenTokenIsValidThenConfirmToken() {
        when(doctorConfirmationTokenService.getToken(anyString())).thenReturn(Optional.of(new DoctorConfirmationToken("token", LocalDateTime.now().minusMinutes(5), LocalDateTime.now().plusMinutes(30), testRegistrationDTO.toDoctor())));

        assertDoesNotThrow(() -> doctorRegistrationService.confirmToken("token"));
    }

    @Test
    public void givenConfirmTokenMethodWhenTokenDoesNotExistsThenThrowTokenNotFoundException() {
        when(doctorConfirmationTokenService.getToken(anyString())).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> doctorRegistrationService.confirmToken("token"));
    }

    @Test
    public void givenConfirmTokenMethodWhenEmailIsAlreadyConfirmedThenThrowEmailAlreadyConfirmedException() {
        DoctorConfirmationToken confirmedToken = new DoctorConfirmationToken("token", LocalDateTime.now().minusMinutes(20), LocalDateTime.now().plusMinutes(5), testRegistrationDTO.toDoctor());
        confirmedToken.setConfirmedAt(LocalDateTime.now().minusMinutes(2));
        when(doctorConfirmationTokenService.getToken(anyString())).thenReturn(Optional.of(confirmedToken));

        assertThrows(EmailAlreadyConfirmedException.class, () -> doctorRegistrationService.confirmToken("token"));
    }
}