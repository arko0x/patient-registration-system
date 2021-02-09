package pl.nikodem.patientregistrationsystem.patient.registration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nikodem.patientregistrationsystem.patient.Patient;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientConfirmationTokenServiceTest {
    @Mock
    private PatientConfirmationTokenRepository tokenRepository;

    @InjectMocks
    private PatientConfirmationTokenService patientConfirmationTokenService;

    @Test
    public void givenGetTokenMethodWhenTokenFoundThenReturnOptionalOfToken() {
        PatientConfirmationToken token = new PatientConfirmationToken("TOKEN", LocalDateTime.now().minusMinutes(10), LocalDateTime.now().plusMinutes(10), new Patient());
        when(tokenRepository.findByToken(any(String.class))).thenReturn(Optional.of(token));
        assertEquals(Optional.of(token), patientConfirmationTokenService.getToken("TOKEN"));
    }

    @Test
    public void givenGetTokenMethodWhenTokenNotFoundThenReturnEmptyOptional() {
        when(tokenRepository.findByToken(any(String.class))).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), patientConfirmationTokenService.getToken("TOKEN"));
    }
}