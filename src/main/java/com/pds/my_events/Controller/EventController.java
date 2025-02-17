package com.pds.my_events.Controller;

import com.pds.my_events.Exception.ResourceNotFoundException;
import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.User;
import com.pds.my_events.Repository.UserRepository;
import com.pds.my_events.Repository.EventRepository;
import com.pds.my_events.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Event> createEvent(@PathVariable Long userId, @RequestBody Event event) {
        try {
            Event createdEvent = eventService.createEvent(userId, event);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{userId}/{id}")
    public Event getEventById(@PathVariable Long userId, @PathVariable Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    @PutMapping("/{userId}/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody Event eventDetails) {

        Event updatedEvent = eventService.updateEvent(userId, id, eventDetails);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{userId}/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long userId, @PathVariable Long id) {
        eventService.deleteEvent(userId, id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}
