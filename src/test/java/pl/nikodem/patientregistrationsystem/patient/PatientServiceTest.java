package pl.nikodem.patientregistrationsystem.patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void givenLoadUserByUsernameWhenUsernameNotFoundThenThrowUsernameNotFoundException() {
        when(patientRepository.findByUsername(any(String.class))).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> patientService.loadUserByUsername("username"));
    }

    @Test
    void givenFindUserByEmailWhenEmailNotFoundThenReturnNull() {
        when(patientRepository.findPatientByEmail(any(String.class))).thenReturn(null);

        assertEquals(null, patientService.findUserByEmail("email"));
    }
}