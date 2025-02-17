package com.pds.my_events.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pds.my_events.Model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {}

