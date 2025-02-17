package com.pds.my_events.Service;

import com.pds.my_events.Exception.ResourceNotFoundException;
import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.Participation;
import com.pds.my_events.Model.User;
import com.pds.my_events.Repository.ParticipationRepository;
import com.pds.my_events.Repository.EventRepository;
import com.pds.my_events.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Set;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public Participation addParticipation(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));

        if (participationRepository.existsByUserAndEvent(user, event)) {
            throw new IllegalArgumentException("User is already registered in this event.");
        }

        Participation participation = new Participation(user, event);
        Participation savedParticipation = participationRepository.save(participation);

        savedParticipation.getUser().setParticipations(null);
        savedParticipation.getEvent().setParticipations(null);

        return savedParticipation;
    }


    public void cancelRegistration(Long userId, Long eventId) {
        Participation participation = participationRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Participation not found"));

        participationRepository.delete(participation);
    }

    public Set<Participation> getParticipationsByEvent(Long eventId, Long userId) throws AccessDeniedException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
        if (!event.getOrganizer().getId().equals(userId)) {
            throw new AccessDeniedException("Only the event organizer can view the list of participants.");
        }

        return event.getParticipations(); // Retorna os participantes apenas se o usuário for organizador
    }


    public Set<Participation> getParticipationsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        return user.getParticipations();
    }
}