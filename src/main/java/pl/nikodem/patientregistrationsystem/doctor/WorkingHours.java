package pl.nikodem.patientregistrationsystem.doctor;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkingHours {
    private LocalTime startTime;
    private LocalTime endTime;

    public WorkingHours(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static List<WorkingHours> createListOfAvailableHoursWithGivenMeetingTime(LocalTime startTime, LocalTime endTime, long meetingTimeInMinutes) {
        List<WorkingHours> list = new ArrayList<>();
        while (startTime.compareTo(endTime) < 0) {
            if (startTime.plusMinutes(meetingTimeInMinutes).isBefore(endTime.plusMinutes(1))) {
                list.add(new WorkingHours(startTime, startTime.plusMinutes(meetingTimeInMinutes)));
                startTime = startTime.plusMinutes(meetingTimeInMinutes);
            } else break;
        }
        return list;
    }

    public boolean containsBetweenStartHourAndEndHour(Instant time) {
        if (startTime.compareTo(LocalTime.ofInstant(time, ZoneId.systemDefault())) <= 0 && endTime.compareTo(LocalTime.ofInstant(time, ZoneId.systemDefault())) > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingHours that = (WorkingHours) o;
        return Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }
}
