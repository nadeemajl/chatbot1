package com.aws.poc.whatsapp.chatbot1.networkingMatch;



import com.aws.poc.whatsapp.chatbot1.ChatbotClient;
import com.aws.poc.whatsapp.chatbot1.dao.MeetingDao;
import com.aws.poc.whatsapp.chatbot1.dao.ScheduleDAO;
import com.aws.poc.whatsapp.chatbot1.dao.UserResource;
import com.aws.poc.whatsapp.chatbot1.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkingMatch {
    private UserResource userResource;
    private MeetingDao meetingDao;
    private ScheduleDAO scheduleDao;

    public NetworkingMatch(User user1, Region apac, String sales) {

    }

    public List<Schedule> findFreeTimeSlotByAreaAndRegion(Region region, String area) {
        List<User> expertsInAreaAndRegion = userResource.getUsersByAreaAndRegion(area, region);
        List<Schedule> freeTimeSlots = null;

        for (User expert : expertsInAreaAndRegion) {
            freeTimeSlots.addAll(expert.getFreeTimeSlots());
        }
        return freeTimeSlots;

    }

    public List<Schedule> filterByUserDefinedTimeSlot(LocalDateTime selectedTimeslot, List<Schedule> freeTimeSlotByAreaAndRegion) {
        return freeTimeSlotByAreaAndRegion.stream().filter(timeSlot -> (timeSlot.getStartTime().equals(selectedTimeslot))).collect(Collectors.toList());

    }

    public boolean networkingMatch(User user, Region region, String area) {
        List<Schedule> freeTimeSlotByAreaAndRegion = findFreeTimeSlotByAreaAndRegion(region, area);
        List<LocalDateTime> freeStartTimes = freeTimeSlotByAreaAndRegion.stream().map(schedule -> schedule.getStartTime()).distinct().collect(Collectors.toList());
        LocalDateTime selectedTimeslot = freeStartTimes.get(1); //get from user
        filterByUserDefinedTimeSlot(selectedTimeslot, freeTimeSlotByAreaAndRegion);

        for (Schedule expertSchedule : freeTimeSlotByAreaAndRegion) {
            User expert = userResource.getUserById(expertSchedule.getUserId());

            String askExpertAvailability = "are you available at" + expertSchedule.getStartTime();
            //put temporary meeting in expert calender
            Meeting meeting = new Meeting(user, expert, expertSchedule.getStartTime(), MeetingPoint.A, "pending");
            Schedule userSchedule = user.getScheduleByTimeSlot(expertSchedule);
            persistMeetingSchedule(userSchedule,expertSchedule,meeting);
            String expertResponse = ChatbotClient.sendMessage(expert, askExpertAvailability);
            boolean timeOut = true;

            if (expertResponse.equals("yes")) {
                meeting.setState("accepted");
                persistMeetingSchedule(userSchedule,expertSchedule,meeting);
                String confirm = "meeting booked at" + expertSchedule.getStartTime();
                ChatbotClient.sendMessage(expert, confirm);
                ChatbotClient.sendMessage(user, confirm);
                //send confirmation to user and expert
            } else if (expertResponse.equals("no") || timeOut)
                expertSchedule.setScheduleItem(null);
                userSchedule.setScheduleItem(null);
                scheduleDao.update(expertSchedule);
                scheduleDao.delete(userSchedule);
                meetingDao.delete(meeting);
                continue;
        }

        return false;
    }

    private void persistMeetingSchedule(Schedule userSchedule, Schedule expertSchedule, Meeting meeting) {
        expertSchedule.setScheduleItem(meeting);
        userSchedule.setScheduleItem(meeting);
        scheduleDao.insert(expertSchedule);
        scheduleDao.insert(userSchedule);
        meetingDao.insert(meeting);
    }
}
