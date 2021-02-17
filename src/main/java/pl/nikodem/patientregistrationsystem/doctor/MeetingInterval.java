package pl.nikodem.patientregistrationsystem.doctor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class MeetingInterval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    @JsonBackReference(value = "doctor-meetingInterval")
    private Doctor doctor;

    public MeetingInterval(LocalDate date, LocalTime startTime, LocalTime endTime, Doctor doctor) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingInterval that = (MeetingInterval) o;
        return date.equals(that.date) && startTime.equals(that.startTime) && endTime.equals(that.endTime) && doctor.equals(that.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, startTime, endTime, doctor);
    }
}
