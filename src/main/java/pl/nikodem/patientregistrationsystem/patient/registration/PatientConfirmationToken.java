package pl.nikodem.patientregistrationsystem.patient.registration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nikodem.patientregistrationsystem.patient.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PatientConfirmationToken {
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
            name = "patient_id"
    )
    private Patient patient;

    private LocalDateTime confirmedAt;

    public PatientConfirmationToken(String token,
                                    LocalDateTime createdAt,
                                    LocalDateTime expiredAt,
                                    Patient patient) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.patient = patient;
    }
}
