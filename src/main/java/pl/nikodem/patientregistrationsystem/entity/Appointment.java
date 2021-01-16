package pl.nikodem.patientregistrationsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    private Instant date;

    public Appointment(Doctor doctor, Patient patient, Instant date) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
    }
}
