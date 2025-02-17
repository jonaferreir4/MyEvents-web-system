package com.pds.my_events.Service;

import com.pds.my_events.Exception.ResourceNotFoundException;
import com.pds.my_events.Model.Activity;
import com.pds.my_events.Model.Certificate;
import com.pds.my_events.Model.User;
import com.pds.my_events.Repository.ActivityRepository;
import com.pds.my_events.Repository.CertificateRepository;
import com.pds.my_events.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    public Certificate generateCertificate(Long activityId, Long userId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id " + activityId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Certificate certificate = new Certificate();
        certificate.setActivity(activity);
        certificate.setUser(user); // Adicionado relacionamento com usuário
        certificate.setFullName(user.getName());
        certificate.setTotalHours(activity.getCertificateHours());

        return certificateRepository.save(certificate);
    }

    public List<Certificate> getCertificatesByUser(Long userId) {
        return certificateRepository.findAllByUserId(userId);
    }

    public void deleteCertificate(Long certificateId) {
        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with id " + certificateId));
        certificateRepository.delete(certificate);
    }
}
