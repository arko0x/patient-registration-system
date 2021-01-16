package pl.nikodem.patientregistrationsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.nikodem.patientregistrationsystem.security.ApplicationUserRole;

import javax.persistence.*;
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

    private String username;

    private String password;

    private String role;

    private Instant createdAt;

    @Column(table = "patient_details")
    private String firstName;

    @Column(table = "patient_details")
    private String lastName;

    @Column(table = "patient_details")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    public Patient(long id, String username, String password, String role, Instant createdAt, String firstName, String lastName, LocalDate birthDate) {
        this.id = id;
        this.username = username;
        this.password = password;
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addAppointment(Appointment appointment, Doctor doctor) {
        appointments.add(appointment);
        doctor.addAppointment(appointment);
    }
}
