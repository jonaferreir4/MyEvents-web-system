package com.pds.my_events.Mapper;

import com.pds.my_events.Model.Activity;
import org.springframework.stereotype.Component;
import com.pds.my_events.Model.Certificate;
import com.pds.my_events.dto.CertificateDTO;

@Component
public class CertificateMapper {

    public CertificateDTO toDTO(Certificate certificate) {
        CertificateDTO dto = new CertificateDTO();
        dto.setId(certificate.getId());
        dto.setActivityName(certificate.getActivity().getName());
        dto.setActivityDate(certificate.getActivity().getDate());
        dto.setFullName(certificate.getFullName());
        dto.setTotalHours(certificate.getTotalHours());
        return dto;
    }

    public Certificate toEntity(CertificateDTO dto, Activity activity) {
        Certificate certificate = new Certificate();
        certificate.setId(dto.getId());
        certificate.setActivity(activity);
        certificate.setFullName(dto.getFullName());
        certificate.setTotalHours(dto.getTotalHours());
        return certificate;
    }
}
