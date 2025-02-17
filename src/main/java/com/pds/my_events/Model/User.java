package com.pds.my_events.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String CPF;
    private int enrollment;
    private String email;
    private String password;


    // Eventos organizados
    @OneToMany(mappedBy = "organizer")
    private Set<Event> organizedEvents;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participation> participations;

    @OneToMany(mappedBy = "speaker")
    private Set<Activity> activitiesAsSpeaker; // Atividades em que o usuário é palestrante
}
