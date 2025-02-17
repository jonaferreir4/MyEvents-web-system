package com.pds.my_events.Repository;

import com.pds.my_events.Model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findAllByUserId(Long userId);
}
