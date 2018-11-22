package com.aws.poc.whatsapp.chatbot1.model;

import java.util.List;

public class User {
    private List<Schedule> scheduleList;
    private String id;



    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Schedule getScheduleByTimeSlot(Schedule expertSchedule) {
        return null;
    }

    public List<Schedule> getFreeTimeSlots() {
        return null;
    }
}
