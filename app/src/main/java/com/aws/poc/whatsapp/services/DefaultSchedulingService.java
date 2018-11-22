package com.aws.poc.whatsapp.services;

import com.aws.poc.whatsapp.model.Meeting;
import com.aws.poc.whatsapp.model.Schedule;

import java.time.Duration;

public class DefaultSchedulingService implements SchedulingService {
    @Override
    public void validateUserAvailability(String UserId, Duration timeslot) {

    }

    @Override
    public void remindUsers(Meeting meeting) {

    }

    @Override
    public Schedule schedule(Meeting meeting) {
        return null;
    }

    @Override
    public void cancelMeetingReminder(Meeting meeting) {

    }
}
