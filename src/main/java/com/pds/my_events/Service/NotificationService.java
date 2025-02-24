package com.pds.my_events.Service;

import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.Notification;
import com.pds.my_events.Model.Participation;
import com.pds.my_events.Model.User;
import com.pds.my_events.Observer.Publisher;
import com.pds.my_events.Repository.EventRepository;
import com.pds.my_events.Repository.NotificationRepository;
import com.pds.my_events.Repository.ParticipationRepository;
import com.pds.my_events.Repository.UserRepository;
import io.micrometer.observation.Observation;
import org.apache.tomcat.util.modeler.NotificationInfo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;

import java.util.*;

@Service
public class NotificationService  implements Publisher {


    private List<User> observers = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    /*
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

            }
        }
    }

     */


    public List<Notification> getAllNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        // Inicializa as relações LAZY manualmente
        for (Notification notification : notifications) {
            Hibernate.initialize(notification.getUser());
            Hibernate.initialize(notification.getEvent());
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

    @Transactional
    private void persistence(ArrayList<Notification> notifications) {
        notificationRepository.saveAll(notifications);
        userRepository.saveAll(observers);
    }




    @Override
    public void addObserver(User observer) {
        this.observers.add(observer);
    }


    @Override
    public void notifyObservers(String message, Event event) {
      var  notificationsSended = new ArrayList<Notification>();


        for (User observer : new ArrayList<>(observers)) {
            Notification notification = new Notification();
            notification.setEvent(event);
            notification.setMessage(message);

            notification.setUser(observer);
            observer.update(notification);
            notificationsSended.add(notification);

        }
        persistence(notificationsSended);
    }
}