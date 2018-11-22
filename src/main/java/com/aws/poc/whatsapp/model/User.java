package com.aws.poc.whatsapp.model;

import java.util.List;

public class User {
    private List<Schedule> scheduleList;
    private String id;
    private String name;
    private Region region;
    private String is_expert;
    private String area;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getIs_expert() {
        return is_expert;
    }

    public void setIs_expert(String is_expert) {
        this.is_expert = is_expert;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Schedule getScheduleByTimeSlot(Schedule expertSchedule) {
        return null;
    }

    public List<Schedule> getFreeTimeSlots() {
        return null;
    }
}
