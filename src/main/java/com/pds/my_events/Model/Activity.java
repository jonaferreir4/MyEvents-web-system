package com.pds.my_events.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String theme;
    private String type;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int maxCapacity;
    private LocalTime certificateHours;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;


    @ManyToOne
    @JoinColumn(name = "speaker_id")
    private User speaker; // Relacionamento com o palestrante

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    private Set<Evaluation> evaluations = Collections.synchronizedSet(new HashSet<>()); // Lista de avaliações
}
