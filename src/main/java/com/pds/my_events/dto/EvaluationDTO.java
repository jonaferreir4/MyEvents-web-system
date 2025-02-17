package com.pds.my_events.dto;

import lombok.Data;

@Data
public class EvaluationDTO {
    private Long id;
    private int rating;
    private String comment;
    private Long activityId;
    private String activityName;
    private Long userId;
    private String userName;
}
