package com.aws.poc.whatsapp.services;

import com.aws.poc.whatsapp.model.Meeting;
import com.aws.poc.whatsapp.model.Schedule;

import java.time.Duration;

public interface SchedulingService {
    public void validateUserAvailability(String UserId, Duration timeslot);
    public void remindUsers(Meeting meeting);
    public Schedule schedule(Meeting meeting);
    public void cancelMeetingReminder(Meeting meeting);
}
