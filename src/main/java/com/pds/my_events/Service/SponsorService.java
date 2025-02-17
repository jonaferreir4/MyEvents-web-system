package com.pds.my_events.Service;

import com.pds.my_events.Exception.ResourceNotFoundException;
import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.Sponsor;
import com.pds.my_events.Repository.EventRepository;
import com.pds.my_events.Repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SponsorService {

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private EventRepository eventRepository;

    public Sponsor addSponsorToEvent(Long organizerId, Long eventId, Sponsor sponsor) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));

        if (!event.getOrganizer().getId().equals(organizerId)) {
            throw new IllegalStateException("User is not the organizer of this event");
        }

        event.getSponsors().add(sponsor);
        sponsorRepository.save(sponsor);
        eventRepository.save(event);
        return sponsor;
    }

    public Sponsor updateSponsor(Long organizerId, Long eventId, Long sponsorId, Sponsor updatedSponsor) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));

        if (!event.getOrganizer().getId().equals(organizerId)) {
            throw new IllegalStateException("User is not the organizer of this event");
        }

        Sponsor sponsor = sponsorRepository.findById(sponsorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsor not found with id " + sponsorId));

        sponsor.setName(updatedSponsor.getName());
        sponsor.setLogoUrl(updatedSponsor.getLogoUrl());
        sponsor.setSocial(updatedSponsor.getSocial());

        return sponsorRepository.save(sponsor);
    }

    public void removeSponsorFromEvent(Long organizerId, Long eventId, Long sponsorId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));

        if (!event.getOrganizer().getId().equals(organizerId)) {
            throw new IllegalStateException("User is not the organizer of this event");
        }

        Sponsor sponsor = sponsorRepository.findById(sponsorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsor not found with id " + sponsorId));

        event.getSponsors().remove(sponsor);
        sponsorRepository.delete(sponsor);
        eventRepository.save(event);
    }

    public List<Sponsor> getAllSponsorsForEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));

        return List.copyOf(event.getSponsors());
    }
}
