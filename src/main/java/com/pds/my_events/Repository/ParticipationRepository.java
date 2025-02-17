package com.pds.my_events.Repository;


import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pds.my_events.Model.Participation;

import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    boolean existsByUserAndEvent(User user, Event event);
    Optional<Participation> findByUserIdAndEventId(Long userId, Long eventId);
}

