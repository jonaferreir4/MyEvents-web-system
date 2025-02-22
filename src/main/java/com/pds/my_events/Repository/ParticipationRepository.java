package com.pds.my_events.Repository;

import com.pds.my_events.Model.Event;
import com.pds.my_events.Model.User;
import com.pds.my_events.Model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    boolean existsByUserAndEvent(User user, Event event);
    Optional<Participation> findById(Long id);
    List<Participation> findAllByEventId(Long eventId);
    List<Participation> findByUserId(Long userId);
}