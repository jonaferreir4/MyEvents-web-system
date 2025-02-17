package com.pds.my_events.Controller;

import com.pds.my_events.dto.ActivityDTO;
import com.pds.my_events.Model.Activity;
import com.pds.my_events.Service.ActivityService;
import com.pds.my_events.Mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityMapper activityMapper;

    // Criar uma nova atividade vinculada a um evento organizado pelo usuário
    @PostMapping("/{userId}/{eventId}")
    public ResponseEntity<ActivityDTO> createActivity(@PathVariable Long userId, @PathVariable Long eventId, @RequestBody ActivityDTO activityDTO) {
        Activity activity = activityMapper.toEntity(activityDTO, null, null); // Inicialmente sem evento e palestrante
        Activity createdActivity = activityService.createActivity(userId, eventId, activity);
        ActivityDTO createdActivityDTO = activityMapper.toDTO(createdActivity);
        return ResponseEntity.ok(createdActivityDTO);
    }

    // Obter todas as atividades
    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        List<ActivityDTO> activityDTOs = activities.stream()
                .map(activityMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(activityDTOs);
    }

    // Obter uma atividade por ID
    @GetMapping("/{userId}/{eventId}/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) {
        Activity activity = activityService.getActivityById(id);
        ActivityDTO activityDTO = activityMapper.toDTO(activity);
        return ResponseEntity.ok(activityDTO);
    }

    // Atualizar uma atividade (somente organizadores podem atualizar suas atividades)
    @PutMapping("/{userId}/{activityId}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long userId, @PathVariable Long activityId, @RequestBody ActivityDTO updatedActivityDTO) {
        Activity updatedActivity = activityMapper.toEntity(updatedActivityDTO, null, null); // Inicialmente sem evento e palestrante
        Activity activity = activityService.updateActivity(userId, activityId, updatedActivity);
        ActivityDTO activityDTO = activityMapper.toDTO(activity);
        return ResponseEntity.ok(activityDTO);
    }

    // Deletar uma atividade (somente organizadores podem deletar suas atividades)
    @DeleteMapping("/{userId}/{activityId}")
    public ResponseEntity<String> deleteActivity(@PathVariable Long userId, @PathVariable Long activityId) {
        activityService.deleteActivity(userId, activityId);
        return ResponseEntity.ok("Activity deleted successfully.");
    }
}