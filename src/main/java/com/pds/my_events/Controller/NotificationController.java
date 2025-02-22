package com.pds.my_events.Controller;


import com.pds.my_events.Mapper.EventMapper;
import com.pds.my_events.Mapper.NotificationMapper;
import com.pds.my_events.Model.Notification;
import com.pds.my_events.Service.NotificationService;
import com.pds.my_events.dto.EventDTO;
import com.pds.my_events.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    private final NotificationMapper notificationMapper;

    public NotificationController(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getAllNotificationsByUserId(userId);
        List<NotificationDTO> notificationDTOs = notifications.stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificationDTOs);
    }

    @DeleteMapping("/{userId}/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long userId, @PathVariable Long id) {
        try {
            notificationService.deleteNotification(userId, id);
            return ResponseEntity.ok("Notification deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while deleting the notification.");
        }
    }





}
