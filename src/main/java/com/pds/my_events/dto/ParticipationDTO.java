package com.pds.my_events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipationDTO {
    private Long id;
    private Long userId;
    private Long eventId;
}