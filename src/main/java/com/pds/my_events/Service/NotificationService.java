package com.pds.my_events.Service;

import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.Notification;
import com.pds.my_events.Model.Participation;
import com.pds.my_events.Model.User;
import com.pds.my_events.Repository.EventRepository;
import com.pds.my_events.Repository.NotificationRepository;
import com.pds.my_events.Repository.ParticipationRepository;
import com.pds.my_events.Repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Transactional
    public void notify(Long userId, Long eventId, Long participationId,Notification notification) {

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        Optional<Participation> participationOpt = participationRepository.findById(participationId);

        if (userOpt.isPresent() && eventOpt.isPresent() && participationOpt.isPresent()) {
            User user = userOpt.get();
            Event event = eventOpt.get();
            Participation participation = participationOpt.get();

            if (user.getId().equals(participation.getUser().getId()) &&
                    event.getId().equals(participation.getEvent().getId())) {
                user.update(notification);
                notification.setParticipation(participation);
                notification.setUser(user);
                notificationRepository.save(notification);
                userRepository.save(user);
            }
        }
    }


    public List<Notification> getAllNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        // Inicializa as relações LAZY manualmente
        for (Notification notification : notifications) {
            Hibernate.initialize(notification.getUser());
            Hibernate.initialize(notification.getParticipation());
        }
        return notifications;
    }

    public void deleteNotification(Long userId, Long id) {
        Optional<Notification> notificationOpt = notificationRepository.findById(id);
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            User user = notification.getUser();

            // Valida se o User existe e se o ID corresponde
            if (user != null && user.getId().equals(userId)) {
                notificationRepository.delete(notification);
            } else {
                throw new IllegalArgumentException("User not found or does not match the notification's user.");
            }
        } else {
            throw new IllegalArgumentException("Notification not found with id " + id);
        }
    }
}