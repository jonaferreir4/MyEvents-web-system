package com.pds.my_events.Controller;

import com.pds.my_events.Mapper.SponsorMapper;
import com.pds.my_events.Model.Sponsor;
import com.pds.my_events.Service.SponsorService;
import com.pds.my_events.dto.SponsorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sponsors")
public class SponsorController {

    @Autowired
    private SponsorService sponsorService;

    @Autowired
    private SponsorMapper sponsorMapper;

    @PostMapping("/{organizerId}/{eventId}")
    public ResponseEntity<SponsorDTO> addSponsorToEvent(@PathVariable Long organizerId,
                                                        @PathVariable Long eventId,
                                                        @RequestBody SponsorDTO sponsorDTO) {
        Sponsor sponsor = sponsorMapper.toEntity(sponsorDTO);
        Sponsor createdSponsor = sponsorService.addSponsorToEvent(organizerId, eventId, sponsor);
        SponsorDTO createdSponsorDTO = sponsorMapper.toDTO(createdSponsor);
        return ResponseEntity.ok(createdSponsorDTO);
    }

    @PutMapping("/{organizerId}/{eventId}/{sponsorId}")
    public ResponseEntity<SponsorDTO> updateSponsor(@PathVariable Long organizerId,
                                                    @PathVariable Long eventId,
                                                    @PathVariable Long sponsorId,
                                                    @RequestBody SponsorDTO sponsorDTO) {
        Sponsor updatedSponsor = sponsorMapper.toEntity(sponsorDTO);
        Sponsor sponsor = sponsorService.updateSponsor(organizerId, eventId, sponsorId, updatedSponsor);
        SponsorDTO sponsorDTOResponse = sponsorMapper.toDTO(sponsor);
        return ResponseEntity.ok(sponsorDTOResponse);
    }

    @DeleteMapping("/{organizerId}/{eventId}/{sponsorId}")
    public ResponseEntity<String> removeSponsorFromEvent(@PathVariable Long organizerId,
                                                         @PathVariable Long eventId,
                                                         @PathVariable Long sponsorId) {
        sponsorService.removeSponsorFromEvent(organizerId, eventId, sponsorId);
        return ResponseEntity.ok("Sponsor removed successfully.");
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<SponsorDTO>> getAllSponsorsForEvent(@PathVariable Long eventId) {
        List<Sponsor> sponsors = sponsorService.getAllSponsorsForEvent(eventId);
        List<SponsorDTO> sponsorDTOs = sponsors.stream()
                .map(sponsorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sponsorDTOs);
    }
}
