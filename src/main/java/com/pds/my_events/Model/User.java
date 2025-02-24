package com.pds.my_events.Model;

import com.pds.my_events.Observer.Subscribe;
import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String CPF;
    private int enrollment;
    private String email;
    private String password;


    @OneToMany(mappedBy = "organizer")
    private Set<Event> organizedEvents = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Participation> participations = new CopyOnWriteArraySet<>();

    @OneToMany(mappedBy = "speaker")
    private Set<Activity> activitiesAsSpeaker = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Notification> notifications = new ArrayList<>();


    @Override
    public void update(Notification notification) {
        notifications.add(notification);
    }
}
