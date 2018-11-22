package com.aws.poc.whatsapp.controller;

import com.aws.poc.whatsapp.dao.TalkDao;
import com.aws.poc.whatsapp.services.SchedulingService;

public class ActivitiesNotification {
    SchedulingService schedulingService;
    TalkDao talkDao;

    public ActivitiesNotification(SchedulingService schedulingService, TalkDao talkDao) {
        this.schedulingService = schedulingService;
        this.talkDao = talkDao;
    }

}
