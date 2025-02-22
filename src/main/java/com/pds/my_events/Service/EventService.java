package com.pds.my_events.Service;

import com.pds.my_events.Exception.ResourceNotFoundException;
import com.pds.my_events.Mapper.EventMapper;
import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.Notification;
import com.pds.my_events.Model.Participation;
import com.pds.my_events.Model.User;
import com.pds.my_events.Repository.EventRepository;
import com.pds.my_events.Repository.ParticipationRepository;
import com.pds.my_events.Repository.UserRepository;
import com.pds.my_events.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private NotificationService notificationService;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<EventDTO> getEventsByOrganizer(Long organizerId) {
        List<Event> events = eventRepository.findByOrganizerId(organizerId);
        return events.stream().map(EventMapper::toDTO).collect(Collectors.toList());
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

    public Set<User> getUsersParticipationOfEvent(Long eventId) {
        List<Participation> participations = participationRepository.findAllByEventId(eventId);
        return participations.stream()
                .map(Participation::getUser)
                .collect(Collectors.toSet());
    }


    public Event updateEvent(Long userId, Long eventId, Event eventDetails) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));

        User organizer = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        if (!event.getOrganizer().getId().equals(userId)) {
            throw new IllegalStateException("User is not the organizer of this event");
        }



        // Atualiza os campos do evento
        event.setName(eventDetails.getName());
        event.setTheme(eventDetails.getTheme());
        event.setInitDate(eventDetails.getInitDate());
        event.setFinalDate(eventDetails.getFinalDate());
        event.setLocal(eventDetails.getLocal());
        event.setDescription(eventDetails.getDescription());
        event.setModality(eventDetails.getModality());

        // Cria uma cópia da coleção de participações para evitar ConcurrentModificationException
        List<Participation> participationsCopy = new ArrayList<>(participationRepository.findAllByEventId(eventId));

        for (Participation participation : participationsCopy) {
            User user = participation.getUser();
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setParticipation(participation);
            notification.setMessage("O evento '" + event.getName() + "' foi atualizado.");
            notificationService.notify(user.getId(), event.getId(), participation.getId(), notification);
        }


        // Salva o evento atualizado
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