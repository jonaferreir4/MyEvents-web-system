package com.pds.my_events.Service;

import com.pds.my_events.Exception.ResourceNotFoundException;
import com.pds.my_events.Model.Activity;
import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.User;
import com.pds.my_events.Repository.ActivityRepository;
import com.pds.my_events.Repository.EventRepository;
import com.pds.my_events.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpeakerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public Activity assignSpeakerToActivity(Long organizerId, Long eventId, Long activityId, Long speakerId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        if (!event.getOrganizer().getId().equals(organizerId)) {
            throw new IllegalArgumentException("Only the event organizer can assign speakers.");
        }
        User speaker = userRepository.findById(speakerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Activity activity = activityRepository.findByIdAndEventId(activityId, eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found or does not belong to this event"));
        activity.setSpeaker(speaker);

        return activityRepository.save(activity);
    }
}

