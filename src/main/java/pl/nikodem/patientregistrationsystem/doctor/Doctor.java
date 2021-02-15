package pl.nikodem.patientregistrationsystem.doctor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.nikodem.patientregistrationsystem.appointments.Appointment;
import pl.nikodem.patientregistrationsystem.security.ApplicationUserRole;

import javax.persistence.*;
import java.time.*;
import java.util.*;

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

    private String email;

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
    @JsonBackReference
    private List<Specialization> specializations;

    @OneToMany(mappedBy = "doctor")
    @JsonBackReference
    private List<Appointment> appointments;

    private boolean enabled = false;
    private boolean locked = false;

//    @Transient
//    private Set<DayOfWeek> defaultWorkingDays;
//
//    @Transient
//    private Map<LocalDate, List<WorkingHours>> dateAvailableHoursMap;
//
//    @Transient
//    private LocalTime defaultWorkStartTime;
//
//    @Transient
//    private LocalTime defaultWorkEndTime;
//
//    @Transient
//    private long defaultMeetingTime;
//
//    @Transient
//    private boolean setDefaultWorkingTimeForNextDay;

    @OneToMany(mappedBy = "doctor")
    @JsonBackReference
    private List<MeetingInterval> meetingIntervals;

    public Doctor(long id, String username, String password, String role, Instant createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.appointments = new ArrayList<>();
    }

    public Doctor(String username, String email, String password, String role, Instant createdAt, String firstName, String lastName, LocalDate birthDate, List<Specialization> specializations) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.specializations = specializations;
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

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
}
