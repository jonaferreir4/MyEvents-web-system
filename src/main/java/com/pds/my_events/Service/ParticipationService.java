package com.pds.my_events.Service;

import com.pds.my_events.Exception.ResourceNotFoundException;
import com.pds.my_events.Mapper.ParticipationMapper;
import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.Participation;
import com.pds.my_events.Model.User;
import com.pds.my_events.Repository.ParticipationRepository;
import com.pds.my_events.Repository.EventRepository;
import com.pds.my_events.Repository.UserRepository;
import com.pds.my_events.dto.ParticipationDTO;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParticipationService {
    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    private final ParticipationMapper participationMapper;




    public ParticipationDTO addParticipation(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
        if (LocalDate.now().isBefore(event.getInitDate()) || LocalDate.now().isAfter(event.getFinalDate())) {
            throw new IllegalArgumentException("Registrations are only allowed during the event period.");
        }
        if (participationRepository.existsByUserAndEvent(user, event)) {
            throw new IllegalArgumentException("User is already registered in this event.");
        }
        Participation participation = new Participation(user, event);
        Participation savedParticipation = participationRepository.save(participation);
        return participationMapper.toDTO(savedParticipation);
    }


    public void cancelRegistration(Long participationId) {
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new ResourceNotFoundException("Participation not found with id " + participationId));
        participationRepository.delete(participation);
    }


    public List<ParticipationDTO> getParticipationsByUser(Long userId) {
        List<Participation> participations = participationRepository.findByUserId(userId);

        // Converte as participações para DTO
        return participations.stream()
                .map(participationMapper::toDTO)
                .collect(Collectors.toList());
    }
}