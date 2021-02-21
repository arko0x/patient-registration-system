package pl.nikodem.patientregistrationsystem.doctor.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
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
import pl.nikodem.patientregistrationsystem.doctor.Specialization;
import pl.nikodem.patientregistrationsystem.doctor.SpecializationType;
import pl.nikodem.patientregistrationsystem.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class DoctorRegistrationControllerTest {
    @MockBean
    private DoctorRegistrationService doctorRegistrationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private DoctorRegistrationDTO getDoctorRegistrationDTOWithInvalidEmail() {
        DoctorRegistrationDTO invalidDoctor = new DoctorRegistrationDTO();
        invalidDoctor.setUsername("username");
        invalidDoctor.setPassword("password");
        invalidDoctor.setRole("ROLE_DOCTOR");
        invalidDoctor.setEmail("email.com"); // incorrect email
        invalidDoctor.setBirthDate(LocalDate.now().minusYears(25));
        invalidDoctor.setFirstName("FirstName");
        invalidDoctor.setLastName("LastName");
        invalidDoctor.setSpecializations(Lists.newArrayList(new Specialization(SpecializationType.DERMATOLOGY)));

        return invalidDoctor;
    }

    private DoctorRegistrationDTO getCorrectDoctorRegistrationDTO() {
        DoctorRegistrationDTO correctDoctor = new DoctorRegistrationDTO();
        correctDoctor.setUsername("username");
        correctDoctor.setPassword("password");
        correctDoctor.setRole("ROLE_DOCTOR");
        correctDoctor.setEmail("someguy@gmail.com");
        correctDoctor.setBirthDate(LocalDate.now().minusYears(25));
        correctDoctor.setFirstName("FirstName");
        correctDoctor.setLastName("LastName");

        return correctDoctor;
    }

    @Test
    void givenRegisterWhenSomeValueIsInvalidThenReturnNotAcceptableStatus() throws Exception {
        mockMvc.perform(
                post("/doctor/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDoctorRegistrationDTOWithInvalidEmail()))
        ).andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));
    }

    @Test
    void givenRegisterWhenAllValuesAreValidThenReturnOkStatus() throws Exception {
        mockMvc.perform(
                post("/doctor/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCorrectDoctorRegistrationDTO()))
        ).andExpect(status().isOk());
    }

    @Test
    void givenRegisterWhenUsernameAlreadyExistsThenReturnConflictStatus() throws Exception {
        doThrow(new UsernameAlreadyExistsException()).when(doctorRegistrationService).register(getCorrectDoctorRegistrationDTO());
        mockMvc.perform(
                post("/doctor/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCorrectDoctorRegistrationDTO()))
        ).andExpect(status().isConflict());
    }

    @Test
    void givenRegisterWhenEmailAlreadyExistsThenReturnConflictStatus() throws Exception {
        doThrow(new EmailAlreadyExistsException()).when(doctorRegistrationService).register(getCorrectDoctorRegistrationDTO());
        mockMvc.perform(
                post("/doctor/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCorrectDoctorRegistrationDTO()))
        ).andExpect(status().isConflict());
    }

    @Test
    void givenConfirmTokenWhenTokenNotFoundThenReturnNotFoundStatus() throws Exception {
        doThrow(new TokenNotFoundException()).when(doctorRegistrationService).confirmToken(any(String.class));
        mockMvc.perform(get("/doctor/register/confirm?token=sometoken"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenConfirmTokenWhenEmailAlreadyConfirmedThenReturnConflictStatus() throws Exception {
        doThrow(new EmailAlreadyConfirmedException()).when(doctorRegistrationService).confirmToken(any(String.class));
        mockMvc.perform(get("/doctor/register/confirm?token=sometoken"))
                .andExpect(status().isConflict());
    }

    @Test
    void givenConfirmTokenWhenTokenIsExpiredThenReturnExpectationFailedStatus() throws Exception {
        doThrow(new TokenExpiredException(LocalDateTime.now())).when(doctorRegistrationService).confirmToken(any(String.class));
        mockMvc.perform(get("/doctor/register/confirm?token=sometoken"))
                .andExpect(status().isExpectationFailed());
    }
}