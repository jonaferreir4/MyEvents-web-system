package com.pds.my_events.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CertificateDTO {
    private Long id;
    private String activityName;
    private LocalDate activityDate;
    private String fullName;
    private LocalTime totalHours;
}
