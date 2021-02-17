package pl.nikodem.patientregistrationsystem.patient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.nikodem.patientregistrationsystem.appointments.Appointment;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.security.ApplicationUserRole;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SecondaryTable(name = "patient_details")
public class Patient implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
    private LocalDate birthDate;

    @OneToMany(mappedBy = "patient")
    @JsonBackReference
    private List<Appointment> appointments;

    private boolean enabled = false;
    private boolean locked = false;

    public Patient(String username, String password, String email, String role, Instant createdAt, String firstName, String lastName, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.appointments = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ApplicationUserRole.PATIENT.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
