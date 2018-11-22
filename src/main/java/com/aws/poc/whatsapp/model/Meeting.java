package com.aws.poc.whatsapp.model;

import java.time.LocalDateTime;

public class Meeting implements ScheduleItem{
    private User user;
    private User expert;
    private LocalDateTime startTime;
    private MeetingPoint meetingPoint;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getExpert() {
        return expert;
    }

    public void setExpert(User expert) {
        this.expert = expert;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public MeetingPoint getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(MeetingPoint meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;

    public Meeting(User user, User expert, LocalDateTime startTime, MeetingPoint meetingPoint, String state) {
        this.user = user;
        this.expert = expert;
        this.startTime = startTime;
        this.meetingPoint = meetingPoint;
        this.state = state;
    }




}
