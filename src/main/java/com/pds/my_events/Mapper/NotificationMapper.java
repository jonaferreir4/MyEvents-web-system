package com.pds.my_events.Mapper;

import com.pds.my_events.Model.Notification;
import com.pds.my_events.dto.NotificationDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDTO toDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getUser().getId(),
                notification.getParticipation().getId(),
                notification.getMessage()
        );
    }
}