package com.pds.my_events.Controller;

import com.pds.my_events.Model.Evaluation;
import com.pds.my_events.Service.EvaluationService;
import com.pds.my_events.Mapper.EvaluationMapper;
import com.pds.my_events.dto.EvaluationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private EvaluationMapper evaluationMapper;

    @PostMapping("/{activityId}/{userId}")
    public ResponseEntity<EvaluationDTO> createEvaluation(
            @PathVariable Long activityId,
            @PathVariable Long userId,
            @RequestBody EvaluationDTO evaluationDTO) {

        Evaluation evaluation = evaluationMapper.toEntity(evaluationDTO);
        Evaluation savedEvaluation = evaluationService.createEvaluation(activityId, userId, evaluation);
        return ResponseEntity.ok(evaluationMapper.toDTO(savedEvaluation));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<EvaluationDTO>> getEvaluationsByActivity(@PathVariable Long activityId) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByActivity(activityId);
        List<EvaluationDTO> evaluationDTOs = evaluations.stream()
                .map(evaluationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(evaluationDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EvaluationDTO>> getEvaluationsByUser(@PathVariable Long userId) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByUser(userId);
        List<EvaluationDTO> evaluationDTOs = evaluations.stream()
                .map(evaluationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(evaluationDTOs);
    }
}
