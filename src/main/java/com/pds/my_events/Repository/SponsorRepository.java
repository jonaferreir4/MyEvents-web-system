package com.pds.my_events.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.pds.my_events.Model.Sponsor;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {}

