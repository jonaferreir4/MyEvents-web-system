package com.pds.my_events.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pds.my_events.Model.Evaluation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findAllByActivityId(Long activityId);
    List<Evaluation> findAllByUserId(Long userId);
}
