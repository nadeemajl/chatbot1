package com.aws.poc.whatsapp.chatbot1.model;

import java.time.LocalDateTime;

public class Schedule {
    private LocalDateTime startTime;
    private String userId;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setScheduleItem(Meeting meeting) {

    }
}
