package com.pds.my_events.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pds.my_events.Model.Evaluation;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findAllByActivityId(Long activityId);
    List<Evaluation> findAllByUserId(Long userId);
}
