package pl.nikodem.patientregistrationsystem.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.nikodem.patientregistrationsystem.helper.WorkingHours;
import pl.nikodem.patientregistrationsystem.repository.DoctorRepository;

import java.time.LocalDate;

@Component
public class ScheduledTasks {
    private DoctorRepository doctorRepository;

    @Autowired
    public ScheduledTasks(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Scheduled(cron = "0 1 * * *")
    public void addWorkingDaysToDoctors() {
        LocalDate workDayToSet = LocalDate.now().plusDays(7);

        doctorRepository.findAll().forEach(doctor -> {
            if (doctor.isSetDefaultWorkingTimeForNextDay() && doctor.getDefaultWorkingDays().contains(LocalDate.now().plusDays(7).getDayOfWeek())
                    && !doctor.getDateAvailableHoursMap().containsKey(workDayToSet)) {
                doctor.addDayAvailableHours(workDayToSet, WorkingHours.createListOfAvailableHoursWithGivenMeetingTime(doctor.getDefaultWorkStartTime(), doctor.getDefaultWorkEndTime(), doctor.getDefaultMeetingTime()));
            }
        });
    }
}
