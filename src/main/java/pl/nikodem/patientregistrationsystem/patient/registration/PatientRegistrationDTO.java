package pl.nikodem.patientregistrationsystem.patient.registration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import pl.nikodem.patientregistrationsystem.patient.Patient;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Data
public class PatientRegistrationDTO {
    @NotBlank
    @Pattern(regexp = "^[^ ]+$", message = "Username can't contain any whitespaces")
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private String password;

    @NotBlank
    private String role;

    private Instant createdAt;

    @Column(table = "patient_details")
    @NotBlank
    @Pattern(regexp = "^[^ ]+$", message = "First name can't contain any whitespaces")
    private String firstName;

    @Column(table = "patient_details")
    @NotBlank
    private String lastName;

    @Column(table = "patient_details")
    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate birthDate;

    public Patient toPatient() {
        return new Patient(username, password, email, role, createdAt, firstName, lastName, birthDate);
    }
}
