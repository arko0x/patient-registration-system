package pl.nikodem.patientregistrationsystem.appointments;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.nikodem.patientregistrationsystem.doctor.Doctor;
import pl.nikodem.patientregistrationsystem.doctor.MeetingInterval;
import pl.nikodem.patientregistrationsystem.exceptions.AppointmentTimeUnavailableException;
import pl.nikodem.patientregistrationsystem.patient.Patient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private MeetingIntervalRepository meetingIntervalRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    public void givenAddAppointmentAndDeleteMeetingIntervalMethodWhenMeetingIntervalDoesNotExistThrowAppointmentTimeUnavailableException() {
        Doctor doctor = mock(Doctor.class);
        when(doctor.hasAvailableMeetingInterval(any(MeetingInterval.class))).thenReturn(false);

        Patient patient = mock(Patient.class);

        MeetingInterval meetingInterval = mock(MeetingInterval.class);
        when(meetingInterval.getDate()).thenReturn(LocalDate.now());
        when(meetingInterval.getStartTime()).thenReturn(LocalTime.now());

        assertThrows(AppointmentTimeUnavailableException.class, () -> appointmentService.addAppointmentAndDeleteMeetingInterval(doctor, patient, meetingInterval));
    }

    @Test
    public void givenAddAppointmentAndDeleteMeetingIntervalMethodWhenMeetingIntervalExistsThenCreateAppointment() {
        Doctor doctor = mock(Doctor.class);
        when(doctor.hasAvailableMeetingInterval(any(MeetingInterval.class))).thenReturn(true);

        Patient patient = mock(Patient.class);

        MeetingInterval meetingInterval = mock(MeetingInterval.class);
        when(meetingInterval.getDate()).thenReturn(LocalDate.now());
        when(meetingInterval.getStartTime()).thenReturn(LocalTime.now());

        assertDoesNotThrow(() -> appointmentService.addAppointmentAndDeleteMeetingInterval(doctor, patient, meetingInterval));
        verify(appointmentRepository, atLeastOnce()).save(any());
        verify(meetingIntervalRepository, atLeastOnce()).deleteByStartTimeAndDoctor(meetingInterval.getStartTime(), doctor);
    }

    @Test
    public void givenDeleteAppointmentByIdWhenAppointmentDoesNotExistThenReturnFalse() {
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertFalse(appointmentService.deleteAppointmentById(10));
    }

    @Test
    public void givenDeleteAppointmentByIdWhenAppointmentExistsThenReturnTrue() {
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(mock(Appointment.class)));

        assertTrue(appointmentService.deleteAppointmentById(10));
    }

    @Test
    public void givenFindByIdMethodWhenAppointmentDoesNotExistThenReturnEmptyOptional() {
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), appointmentService.findById(10));
    }

    @Test
    public void givenFindByIdMethodWhenAppointmentExistsThenReturnOptionalOfThatAppointment() {
        Appointment appointment = new Appointment();

        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));

        assertEquals(Optional.of(appointment), appointmentService.findById(10));
    }
}