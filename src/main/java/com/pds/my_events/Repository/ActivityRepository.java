package com.pds.my_events.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pds.my_events.Model.Activity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findByIdAndEventId(Long activityId, Long eventId);
}
