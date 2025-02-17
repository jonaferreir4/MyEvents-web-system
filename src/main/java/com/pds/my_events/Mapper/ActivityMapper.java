package com.pds.my_events.Mapper;

import com.pds.my_events.Model.Activity;
import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.User;
import com.pds.my_events.dto.ActivityDTO;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityDTO toDTO(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setTheme(activity.getTheme());
        dto.setType(activity.getType());
        dto.setDate(activity.getDate());
        dto.setStartTime(activity.getStartTime());
        dto.setEndTime(activity.getEndTime());
        dto.setMaxCapacity(activity.getMaxCapacity());
        dto.setCertificateHours(activity.getCertificateHours());
        dto.setEventId(activity.getEvent().getId());
        dto.setSpeakerId(activity.getSpeaker() != null ? activity.getSpeaker().getId() : null);
        return dto;
    }

    public Activity toEntity(ActivityDTO dto, Event event, User speaker) {
        Activity activity = new Activity();
        activity.setId(dto.getId());
        activity.setName(dto.getName());
        activity.setTheme(dto.getTheme());
        activity.setType(dto.getType());
        activity.setDate(dto.getDate());
        activity.setStartTime(dto.getStartTime());
        activity.setEndTime(dto.getEndTime());
        activity.setMaxCapacity(dto.getMaxCapacity());
        activity.setCertificateHours(dto.getCertificateHours());
        activity.setEvent(event);
        activity.setSpeaker(speaker);
        return activity;
    }
}

