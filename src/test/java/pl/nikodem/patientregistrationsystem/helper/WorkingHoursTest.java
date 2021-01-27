package pl.nikodem.patientregistrationsystem.helper;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WorkingHoursTest {
    @Test
    public void createListOfAvailableHoursWithGivenMeetingTimeTest() {
        var listOfAvailableHoursWithGivenMeetingTime = WorkingHours.createListOfAvailableHoursWithGivenMeetingTime(LocalTime.of(8, 0), LocalTime.of(9, 0), 30);
        List<WorkingHours> list = Lists.newArrayList(new WorkingHours(LocalTime.of(8, 0), LocalTime.of(8, 30)), new WorkingHours(LocalTime.of(8, 30), LocalTime.of(9, 0)));
        assertEquals(listOfAvailableHoursWithGivenMeetingTime, list);
    }

    @Test
    public void createListOfAvailableHoursWithGivenMeetingTimeShouldReturnAnEmptyListWhenStartTimeIsLessOrEqualEndTime() {
        List<WorkingHours> listOfAvailableHoursWithGivenMeetingTime = WorkingHours.createListOfAvailableHoursWithGivenMeetingTime(LocalTime.of(8, 30), LocalTime.of(8, 30), 20);
        assertEquals(listOfAvailableHoursWithGivenMeetingTime, new ArrayList<>());
    }
}
