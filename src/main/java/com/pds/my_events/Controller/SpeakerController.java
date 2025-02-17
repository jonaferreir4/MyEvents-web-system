package com.pds.my_events.Controller;

import com.pds.my_events.Mapper.ActivityMapper;
import com.pds.my_events.Model.Activity;
import com.pds.my_events.Service.SpeakerService;
import com.pds.my_events.dto.ActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class SpeakerController {

    @Autowired
    private SpeakerService speakerService;

    @Autowired
    private ActivityMapper activityMapper;

    @PutMapping("/{organizerId}/{eventId}/{activityId}/{speakerId}")
    public ActivityDTO assignSpeaker(@PathVariable Long eventId,
                                     @PathVariable Long activityId,
                                     @PathVariable Long speakerId,
                                     @PathVariable Long organizerId) {
        Activity activity = speakerService.assignSpeakerToActivity(organizerId, eventId, activityId, speakerId);
        return activityMapper.toDTO(activity);
    }
}
