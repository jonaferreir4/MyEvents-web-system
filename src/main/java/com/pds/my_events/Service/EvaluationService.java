package com.pds.my_events.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pds.my_events.Model.Evaluation;
import com.pds.my_events.Model.Activity;
import com.pds.my_events.Model.User;
import com.pds.my_events.Repository.EvaluationRepository;
import com.pds.my_events.Repository.ActivityRepository;
import com.pds.my_events.Repository.UserRepository;

import java.util.List;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    public Evaluation createEvaluation(Long activityId, Long userId, Evaluation evaluation) {
        Activity activity = activityRepository.findById(activityId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        evaluation.setActivity(activity);
        evaluation.setUser(user);
        return evaluationRepository.save(evaluation);
    }

    public List<Evaluation> getEvaluationsByActivity(Long activityId) {
        return evaluationRepository.findAllByActivityId(activityId);
    }

    public List<Evaluation> getEvaluationsByUser(Long userId) {
        return evaluationRepository.findAllByUserId(userId);
    }
}
