package pl.nikodem.patientregistrationsystem.doctor.registration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorConfirmationTokenServiceImplTest {
    @Mock
    private DoctorConfirmationTokenRepository tokenRepository;

    @InjectMocks
    private DoctorConfirmationTokenServiceImpl doctorConfirmationTokenService;

    @Test
    public void givenGetTokenMethodWhenTokenFoundThenReturnOptionalOfToken() {
        DoctorConfirmationToken token = new DoctorConfirmationToken("TOKEN", LocalDateTime.now().minusMinutes(10), LocalDateTime.now().plusMinutes(10), new Doctor());
        when(tokenRepository.findByToken(any(String.class))).thenReturn(Optional.of(token));
        assertEquals(Optional.of(token), doctorConfirmationTokenService.getToken("TOKEN"));
    }

    @Test
    public void givenGetTokenMethodWhenTokenNotFoundThenReturnEmptyOptional() {
        when(tokenRepository.findByToken(any(String.class))).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), doctorConfirmationTokenService.getToken("TOKEN"));
    }
}