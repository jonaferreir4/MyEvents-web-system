package com.pds.my_events.Mapper;

import com.pds.my_events.dto.EventDTO;
import com.pds.my_events.Model.Event;

public class EventMapper {

    public static EventDTO toDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getTheme(),
                event.getInitDate(),
                event.getFinalDate(),
                event.getLocal(),
                event.getDescription(),
                event.getModality()
        );
    }

    public static Event toEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setId(eventDTO.getId());
        event.setName(eventDTO.getName());
        event.setTheme(eventDTO.getTheme());
        event.setInitDate(eventDTO.getInitDate());
        event.setFinalDate(eventDTO.getFinalDate());
        event.setLocal(eventDTO.getLocal());
        event.setDescription(eventDTO.getDescription());
        event.setModality(eventDTO.getModality());
        return event;
    }
}
