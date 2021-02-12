package pl.nikodem.patientregistrationsystem.doctor;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "specializations")
    @JsonManagedReference
    private List<Doctor> doctors;

    public Specialization(SpecializationType type) {
        if (type != null) this.name = type.name;
    }
}
