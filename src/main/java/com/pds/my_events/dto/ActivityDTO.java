package com.pds.my_events.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ActivityDTO {
    private Long id;
    private String name;
    private String theme;
    private String type;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int maxCapacity;
    private LocalTime certificateHours;
    private Long eventId;
    private Long speakerId;

}

