package pl.nikodem.patientregistrationsystem.doctor.registration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.doctor.Specialization;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
public class DoctorRegistrationDTO {

    @NotBlank
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotBlank
    private String role;

    private Instant createdAt;

    @NotBlank
    @Pattern(regexp = "^[^ ]+$", message = "First name can't contain any whitespaces")
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate birthDate;

    private List<Specialization> specializations;

    public Doctor toDoctor() {
        return new Doctor(username, email, password, role, createdAt, firstName, lastName, birthDate, specializations);
    }
}
