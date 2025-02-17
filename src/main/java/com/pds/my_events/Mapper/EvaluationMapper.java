package com.pds.my_events.Mapper;

import org.springframework.stereotype.Component;
import com.pds.my_events.Model.Evaluation;
import com.pds.my_events.dto.EvaluationDTO;

@Component
public class EvaluationMapper {

    public EvaluationDTO toDTO(Evaluation evaluation) {
        EvaluationDTO dto = new EvaluationDTO();
        dto.setId(evaluation.getId());
        dto.setRating(evaluation.getRating());
        dto.setComment(evaluation.getComment());
        dto.setActivityId(evaluation.getActivity().getId());
        dto.setActivityName(evaluation.getActivity().getName());
        dto.setUserId(evaluation.getUser().getId());
        dto.setUserName(evaluation.getUser().getName());
        return dto;
    }

    public Evaluation toEntity(EvaluationDTO dto) {
        Evaluation evaluation = new Evaluation();
        evaluation.setId(dto.getId());
        evaluation.setRating(dto.getRating());
        evaluation.setComment(dto.getComment());
        return evaluation;
    }
}
