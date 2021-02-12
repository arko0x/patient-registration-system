package pl.nikodem.patientregistrationsystem.doctor.registration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DoctorConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "doctor_id"
    )
    private Doctor doctor;

    private LocalDateTime confirmedAt;

    public DoctorConfirmationToken(String token,
                                    LocalDateTime createdAt,
                                    LocalDateTime expiredAt,
                                    Doctor doctor) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.doctor = doctor;
    }
}
