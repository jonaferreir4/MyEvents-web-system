package com.pds.my_events.Controller;

import com.pds.my_events.Model.Participation;
import com.pds.my_events.Service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Set;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @PostMapping("/{userId}/{eventId}")
    public ResponseEntity<Participation> registerUserInEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {

        Participation participation = participationService.addParticipation(userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(participation);
    }

    @DeleteMapping("/{userId}/{eventId}")
    public ResponseEntity<Void> cancelRegistration(
            @PathVariable Long userId,
            @PathVariable Long eventId) {

        participationService.cancelRegistration(userId, eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/{eventId}")
    public ResponseEntity<Set<Participation>> getParticipationsByEvent(
            @PathVariable Long eventId,
            @PathVariable Long userId) throws AccessDeniedException { // Recebendo o ID do usuário autenticado

        Set<Participation> participations = participationService.getParticipationsByEvent(eventId, userId);
        return ResponseEntity.ok(participations);
    }

/*
    @GetMapping("/{userId}")
    public ResponseEntity<Set<Participation>> getParticipationsByUser(@PathVariable Long userId) {
        Set<Participation> participations = participationService.getParticipationsByUser(userId);
        return ResponseEntity.ok(participations);
    }

 */
}
