package com.pds.my_events.Controller;

import com.pds.my_events.Model.Participation;
import com.pds.my_events.Service.ParticipationService;
import com.pds.my_events.dto.ParticipationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @PostMapping("/{userId}/{eventId}")
    public ResponseEntity<ParticipationDTO> registerUserInEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
            ParticipationDTO participationDTO = participationService.addParticipation(userId, eventId);
            return ResponseEntity.status(HttpStatus.CREATED).body(participationDTO);

    }

    @DeleteMapping("/{participationId}")
    public ResponseEntity<Void> cancelRegistration(
            @PathVariable Long participationId) {

        participationService.cancelRegistration(participationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ParticipationDTO>> getParticipationsByUser(@PathVariable Long userId) {
        List<ParticipationDTO> participations = participationService.getParticipationsByUser(userId);
        return ResponseEntity.ok(participations);
    }
}