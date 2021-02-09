package pl.nikodem.patientregistrationsystem.patient.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.nikodem.patientregistrationsystem.exceptions.*;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class PatientRegistrationControllerTest {
    @MockBean
    private PatientRegistrationService patientRegistrationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private PatientRegistrationDTO getPatientRegistrationDTOWithInvalidEmail() {
        PatientRegistrationDTO invalidPatient = new PatientRegistrationDTO();
        invalidPatient.setUsername("username");
        invalidPatient.setPassword("password");
        invalidPatient.setRole("ROLE_PATIENT");
        invalidPatient.setEmail("email.com"); // incorrect email
        invalidPatient.setBirthDate(LocalDate.now().minusYears(25));
        invalidPatient.setFirstName("FirstName");
        invalidPatient.setLastName("LastName");

        return invalidPatient;
    }

    private PatientRegistrationDTO getCorrectPatientRegistrationDTO() {
        PatientRegistrationDTO correctPatient = new PatientRegistrationDTO();
        correctPatient.setUsername("username");
        correctPatient.setPassword("password");
        correctPatient.setRole("ROLE_PATIENT");
        correctPatient.setEmail("someguy@gmail.com");
        correctPatient.setBirthDate(LocalDate.now().minusYears(25));
        correctPatient.setFirstName("FirstName");
        correctPatient.setLastName("LastName");

        return correctPatient;
    }

    @Test
    void givenRegisterWhenSomeValueIsInvalidThenReturnNotAcceptableStatus() throws Exception {
        mockMvc.perform(
                post("/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getPatientRegistrationDTOWithInvalidEmail()))
        ).andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));
    }

    @Test
    void givenRegisterWhenAllValuesAreValidThenReturnOkStatus() throws Exception {
        mockMvc.perform(
                post("/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getCorrectPatientRegistrationDTO()))
        ).andExpect(status().isOk());
    }

    @Test
    void givenRegisterWhenUsernameAlreadyExistsThenReturnConflictStatus() throws Exception {
        doThrow(new UsernameAlreadyExistsException()).when(patientRegistrationService).register(getCorrectPatientRegistrationDTO());
        mockMvc.perform(
                post("/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getCorrectPatientRegistrationDTO()))
        ).andExpect(status().isConflict());
    }

    @Test
    void givenRegisterWhenEmailAlreadyExistsThenReturnConflictStatus() throws Exception {
        doThrow(new EmailAlreadyExistsException()).when(patientRegistrationService).register(getCorrectPatientRegistrationDTO());
        mockMvc.perform(
                post("/patient/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCorrectPatientRegistrationDTO()))
        ).andExpect(status().isConflict());
    }

    @Test
    void givenConfirmTokenWhenTokenNotFoundThenReturnNotFoundStatus() throws Exception {
        doThrow(new TokenNotFoundException()).when(patientRegistrationService).confirmToken(any(String.class));
        mockMvc.perform(get("/patient/register/confirm?token=sometoken"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenConfirmTokenWhenEmailAlreadyConfirmedThenReturnConflictStatus() throws Exception {
        doThrow(new EmailAlreadyConfirmedException()).when(patientRegistrationService).confirmToken(any(String.class));
        mockMvc.perform(get("/patient/register/confirm?token=sometoken"))
                .andExpect(status().isConflict());
    }

    @Test
    void givenConfirmTokenWhenTokenIsExpiredThenReturnExpectationFailedStatus() throws Exception {
        doThrow(new TokenExpiredException()).when(patientRegistrationService).confirmToken(any(String.class));
        mockMvc.perform(get("/patient/register/confirm?token=sometoken"))
                .andExpect(status().isExpectationFailed());
    }
}
