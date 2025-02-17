package com.pds.my_events.Service;

import com.pds.my_events.Model.Activity;
import com.pds.my_events.Model.Event;
import com.pds.my_events.Repository.ActivityRepository;
import com.pds.my_events.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EventRepository eventRepository;

    // Criar atividade vinculada a um evento, verificando se o usuário é o organizador
    public Activity createActivity(Long userId, Long eventId, Activity activity) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id " + eventId));

        if (!event.getOrganizer().getId().equals(userId)) {
            throw new IllegalStateException("User is not the organizer of this event");
        }

        activity.setEvent(event);
        return activityRepository.save(activity);
    }

    // Buscar todas as atividades
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    // Buscar atividade por ID
    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found with id " + id));
    }

    // Atualizar uma atividade (somente organizadores podem atualizar suas atividades)
    public Activity updateActivity(Long userId, Long activityId, Activity updatedActivity) {
        Activity existingActivity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found with id " + activityId));

        Event event = existingActivity.getEvent();

        if (!event.getOrganizer().getId().equals(userId)) {
            throw new IllegalStateException("User is not the organizer of this event");
        }

        // Atualizando os campos da atividade
        existingActivity.setName(updatedActivity.getName());
        existingActivity.setTheme(updatedActivity.getTheme());
        existingActivity.setType(updatedActivity.getType());
        existingActivity.setDate(updatedActivity.getDate());
        existingActivity.setStartTime(updatedActivity.getStartTime());
        existingActivity.setEndTime(updatedActivity.getEndTime());
        existingActivity.setMaxCapacity(updatedActivity.getMaxCapacity());
        existingActivity.setCertificateHours(updatedActivity.getCertificateHours());
        existingActivity.setSpeaker(updatedActivity.getSpeaker());

        return activityRepository.save(existingActivity);
    }

    // Deletar uma atividade (somente organizadores podem deletar suas atividades)
    public void deleteActivity(Long userId, Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found with id " + activityId));

        Event event = activity.getEvent();

        if (!event.getOrganizer().getId().equals(userId)) {
            throw new IllegalStateException("User is not the organizer of this event");
        }

        activityRepository.delete(activity);
    }
}
