package com.pds.my_events.Mapper;

import org.springframework.stereotype.Component;
import com.pds.my_events.Model.Sponsor;
import com.pds.my_events.dto.SponsorDTO;

@Component
public class SponsorMapper {

    public SponsorDTO toDTO(Sponsor sponsor) {
        SponsorDTO dto = new SponsorDTO();
        dto.setId(sponsor.getId());
        dto.setName(sponsor.getName());
        dto.setLogoUrl(sponsor.getLogoUrl());
        dto.setSocial(sponsor.getSocial());
        return dto;
    }

    public Sponsor toEntity(SponsorDTO dto) {
        Sponsor sponsor = new Sponsor();
        sponsor.setId(dto.getId());
        sponsor.setName(dto.getName());
        sponsor.setLogoUrl(dto.getLogoUrl());
        sponsor.setSocial(dto.getSocial());
        return sponsor;
    }
}
