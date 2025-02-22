package com.pds.my_events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String name;
    private String theme;
    private LocalDate initDate;
    private LocalDate finalDate;
    private String local;
    private String description;
    private String modality;
}
