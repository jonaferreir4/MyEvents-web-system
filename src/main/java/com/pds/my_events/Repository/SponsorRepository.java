package com.pds.my_events.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.pds.my_events.Model.Sponsor;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Long> {}

