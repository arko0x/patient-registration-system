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
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SecondaryTable(name = "doctor_details")
public class Doctor implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    private String role;

    private Instant createdAt;

    @Column(table = "doctor_details")
    private String firstName;

    @Column(table = "doctor_details")
    private String lastName;

    @Column(table = "doctor_details")
    private LocalDate birthDate;

    @ManyToMany
    @JoinTable(name = "doctor_specialization",
    joinColumns = @JoinColumn(name = "doctor_id"),
    inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private List<Specialization> specializations;


    public Doctor(long id, String username, String password, String role, Instant createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ApplicationUserRole.DOCTOR.getAuthorities();
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
}
