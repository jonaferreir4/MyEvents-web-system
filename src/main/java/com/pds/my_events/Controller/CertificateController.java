package com.pds.my_events.Controller;

import com.pds.my_events.Mapper.CertificateMapper;
import com.pds.my_events.Model.Certificate;
import com.pds.my_events.Service.CertificateService;
import com.pds.my_events.dto.CertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private CertificateMapper certificateMapper;

    @PostMapping("/generate/{activityId}/{userId}")
    public ResponseEntity<CertificateDTO> generateCertificate(@PathVariable Long activityId, @PathVariable Long userId) {
        Certificate certificate = certificateService.generateCertificate(activityId, userId);
        CertificateDTO certificateDTO = certificateMapper.toDTO(certificate);
        return ResponseEntity.ok(certificateDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CertificateDTO>> getCertificatesByUser(@PathVariable Long userId) {
        List<Certificate> certificates = certificateService.getCertificatesByUser(userId);
        List<CertificateDTO> certificateDTOs = certificates.stream()
                .map(certificateMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(certificateDTOs);
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<String> deleteCertificate(@PathVariable Long certificateId) {
        certificateService.deleteCertificate(certificateId);
        return ResponseEntity.ok("Certificate deleted successfully.");
    }
}
