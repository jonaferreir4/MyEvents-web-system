package com.pds.my_events.Service;

import com.pds.my_events.Exception.ResourceNotFoundException;
import com.pds.my_events.Mapper.EventMapper;
import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.Notification;
import com.pds.my_events.Model.Participation;
import com.pds.my_events.Model.User;
import com.pds.my_events.Observer.Publisher;
import com.pds.my_events.Repository.EventRepository;
import com.pds.my_events.Repository.ParticipationRepository;
import com.pds.my_events.Repository.UserRepository;
import com.pds.my_events.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService implements Publisher {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private NotificationService notificationService;

    private Set<User> observers = new HashSet<>();
    @Autowired
    private Participation participation;


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


        // Cria uma cópia da coleção de participações para evitar ConcurrentModificationException
        List<Participation> participationsCopy = new ArrayList<>(participationRepository.findAllByEventId(eventId));

        // Atualiza os campos do evento
        event.setName(eventDetails.getName());

        event.setTheme(eventDetails.getTheme());

        if(!event.getInitDate().equals(eventDetails.getInitDate())) {
            event.setInitDate(eventDetails.getInitDate());
            notifyObserversAndSaveNotifications(event,"A data inicial do evento mudou: " + eventDetails.getInitDate(), participationsCopy);
        }

        if(!event.getFinalDate().equals(eventDetails.getFinalDate())) {
            event.setFinalDate(eventDetails.getFinalDate());
            notifyObserversAndSaveNotifications(event,"A data final do evento mudou: " + eventDetails.getFinalDate(), participationsCopy);

        }

        if(!event.getLocal().equals(eventDetails.getLocal())) {
            event.setLocal(eventDetails.getLocal());
            notifyObserversAndSaveNotifications(event,"O local do evento mudou:  " + eventDetails.getLocal(), participationsCopy);

        }
        event.setDescription(eventDetails.getDescription());

        if(!event.getModality().equals(eventDetails.getModality())) {
            event.setModality(eventDetails.getModality());
            notifyObserversAndSaveNotifications(event, "A modalidade do evento mudou:  " + eventDetails.getModality(), participationsCopy);
        }

        return eventRepository.save(event);

    }

    private void notifyObserversAndSaveNotifications(Event event, String message, List<Participation> participations) {
        notifyObservers(participation.getUser(), message);

        // Salva a notificação para cada participação associada ao evento
        for (Participation participation : participations) {
            User user = participation.getUser();
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setParticipation(participation);
            notification.setMessage(message);
            notificationService.notify(user.getId(), event.getId(), participation.getId(), notification);

        }

    }

    public void deleteEvent(Long userId, Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        if (!event.getOrganizer().getId().equals(userId)) {
            throw new IllegalStateException("User is not the organizer of this event");
        }

        eventRepository.delete(event);
    }

    public Participation getLatestParticipation(User observer) {
        List<Participation> participations = participationRepository.findAll();
        return participations.stream()
                .filter(p -> p.getUser().equals(observer))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addObserver(User observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(User observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(User observerTarget, String message) {
        addObserver(observerTarget);
        for (User observer : observers) {
            observer.update(new Notification(message, getLatestParticipation(observer)));
        }
    }

    public void loadObservers() {
        Set<User> newObservers = new HashSet<>();
        List<Participation> participations = participationRepository.findAll();

        for (Participation participation : participations) {
            newObservers.add(participation.getUser());
        }
        observers.clear();
        observers.addAll(newObservers);
    }
}