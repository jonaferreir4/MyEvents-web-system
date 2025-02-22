package com.pds.my_events.Mapper;

import com.pds.my_events.Model.Participation;
import com.pds.my_events.dto.ParticipationDTO;
import org.springframework.stereotype.Component;

@Component // Adicionamos @Component para que o Spring gerencie essa classe
public class ParticipationMapper {

    public ParticipationDTO toDTO(Participation participation) {
        return new ParticipationDTO(
                participation.getId(),
                participation.getUser().getId(),
                participation.getEvent().getId()
        );
    }

    public Participation toEntity(ParticipationDTO participationDTO) {
        Participation participation = new Participation();
        participation.setId(participationDTO.getId());
        return participation;
    }
}