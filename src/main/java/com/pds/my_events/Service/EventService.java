package com.pds.my_events.Service;

import com.pds.my_events.Exception.ResourceNotFoundException;
import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.User;
import com.pds.my_events.Repository.EventRepository;
import com.pds.my_events.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event createEvent(Long userId, Event event) {
        User organizer = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        event.setOrganizer(organizer);
        return eventRepository.save(event);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    public Event updateEvent(Long userId, Long eventId, Event eventDetails) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));

        User organizer = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        event.setName(eventDetails.getName());
        event.setTheme(eventDetails.getTheme());
        event.setInitDate(eventDetails.getInitDate());
        event.setFinalDate(eventDetails.getFinalDate());
        event.setDescription(eventDetails.getDescription());
        event.setModality(eventDetails.getModality());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long userId, Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        if (!event.getOrganizer().getId().equals(userId)) {
            throw new IllegalStateException("User is not the organizer of this event");
        }

        eventRepository.delete(event);
    }

}
