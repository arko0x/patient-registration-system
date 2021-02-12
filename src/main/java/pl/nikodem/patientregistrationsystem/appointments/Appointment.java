package pl.nikodem.patientregistrationsystem.appointments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.patient.Patient;

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
    @JsonManagedReference
    private Doctor doctor;

    @ManyToOne
    @JsonManagedReference
    private Patient patient;

    private Instant date;

    public Appointment(Doctor doctor, Patient patient, Instant date) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
    }
}
