package com.aws.poc.whatsapp.services;

import com.aws.poc.whatsapp.ChatbotClient;
import com.aws.poc.whatsapp.dao.MeetingDao;
import com.aws.poc.whatsapp.dao.ScheduleDao;
import com.aws.poc.whatsapp.dao.UserDao;
import com.aws.poc.whatsapp.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultMatchingService implements MatchingService {
    private UserDao userDao;
    private MeetingDao meetingDao;
    private ScheduleDao scheduleDao;


    public List<Schedule> findFreeTimeSlotByAreaAndRegion(Region region, String area) {
        List<User> expertsInAreaAndRegion = userDao.getExpertByAreaAndRegion(area, region);
        List<Schedule> freeTimeSlots = null;

        for (User expert : expertsInAreaAndRegion) {
            freeTimeSlots.addAll(expert.getFreeTimeSlots());
        }
        return freeTimeSlots;

    }

    public List<Schedule> filterByUserDefinedTimeSlot(LocalDateTime selectedTimeslot, List<Schedule> freeTimeSlotByAreaAndRegion) {
        return freeTimeSlotByAreaAndRegion.stream().filter(timeSlot -> (timeSlot.getStartTime().equals(selectedTimeslot))).collect(Collectors.toList());

    }
    @Override
    public boolean networkingMatch(User user, Region region, String area) {
        List<Schedule> freeTimeSlotByAreaAndRegion = findFreeTimeSlotByAreaAndRegion(region, area);
        List<LocalDateTime> freeStartTimes = freeTimeSlotByAreaAndRegion.stream().map(schedule -> schedule.getStartTime()).distinct().collect(Collectors.toList());

        LocalDateTime selectedTimeslot = freeStartTimes.get(1); //get from user
        filterByUserDefinedTimeSlot(selectedTimeslot, freeTimeSlotByAreaAndRegion);

        for (Schedule expertSchedule : freeTimeSlotByAreaAndRegion) {
            User expert = userDao.getUserById(expertSchedule.getUserId());

            String askExpertAvailability = "are you available at" + expertSchedule.getStartTime();
            //put pending meeting in expert&user schedules
            Meeting meeting = new Meeting(user, expert, expertSchedule.getStartTime(), MeetingPoint.A, "pending");
            Schedule userSchedule = user.getScheduleByTimeSlot(expertSchedule);
            persistMeetingSchedule(userSchedule,expertSchedule,meeting);
            String expertResponse = ChatbotClient.sendMessage(expert, askExpertAvailability);
            boolean timeOut = true;

            if (expertResponse.equals("yes")) {

                meeting.setState("accepted");
                persistMeetingSchedule(userSchedule,expertSchedule,meeting);

                //send confirmation to user and expert
                String confirm = "meeting booked at" + expertSchedule.getStartTime();
                ChatbotClient.sendMessage(expert, confirm);
                ChatbotClient.sendMessage(user, confirm);

            } else if (expertResponse.equals("no") || timeOut)
                expertSchedule.setScheduleItem(null);
            userSchedule.setScheduleItem(null);
            scheduleDao.delete(expertSchedule);
            scheduleDao.delete(userSchedule);
            meetingDao.delete(meeting);
            continue;
        }

        return false;
    }

    public void persistMeetingSchedule(Schedule userSchedule, Schedule expertSchedule, Meeting meeting) {
        expertSchedule.setScheduleItem(meeting);
        userSchedule.setScheduleItem(meeting);
        scheduleDao.update(expertSchedule);
        scheduleDao.update(userSchedule);
        meetingDao.update(meeting);
    }
}
