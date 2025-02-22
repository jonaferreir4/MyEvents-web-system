package com.pds.my_events.Controller;

import com.pds.my_events.Exception.ResourceNotFoundException;
import com.pds.my_events.Mapper.EventMapper;
import com.pds.my_events.dto.EventDTO;
import com.pds.my_events.Model.Event;
import com.pds.my_events.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        List<EventDTO> eventDTOs = events.stream().map(EventMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }

    @GetMapping("/organizer/{organizerId}")
    public List<EventDTO> getEventsByOrganizer(@PathVariable Long organizerId) {
        return eventService.getEventsByOrganizer(organizerId);
    }


    @PostMapping("/{userId}")
    public ResponseEntity<EventDTO> createEvent(@PathVariable Long userId, @RequestBody EventDTO eventDTO) {
        try {
            Event event = EventMapper.toEntity(eventDTO);
            Event createdEvent = eventService.createEvent(userId, event);
            EventDTO createdEventDTO = EventMapper.toDTO(createdEvent);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEventDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{userId}/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long userId, @PathVariable Long id) {
        Event event = eventService.getEventById(id);
        EventDTO eventDTO = EventMapper.toDTO(event);
        return ResponseEntity.ok(eventDTO);
    }

    @PutMapping("/{userId}/{id}")
    public ResponseEntity<EventDTO> updateEvent(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody EventDTO eventDetailsDTO) {

        Event eventDetails = EventMapper.toEntity(eventDetailsDTO);
        Event updatedEvent = eventService.updateEvent(userId, id, eventDetails);
        EventDTO updatedEventDTO = EventMapper.toDTO(updatedEvent);
        return ResponseEntity.ok(updatedEventDTO);
    }

    @DeleteMapping("/{userId}/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long userId, @PathVariable Long id) {
        eventService.deleteEvent(userId, id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}
