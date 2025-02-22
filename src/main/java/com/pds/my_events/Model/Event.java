package com.pds.my_events.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pds.my_events.Observer.Subscribe;
import com.pds.my_events.Observer.Publisher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String theme;
    private LocalDate initDate;
    private LocalDate finalDate;
    private String local;
    private String description;
    private String modality;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participation> participations = new HashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Activity> activities;

    @ManyToMany
    @JoinTable(
            name = "event_sponsors",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "sponsor_id")
    )
    private Set<Sponsor> sponsors;

    @Transient
    private Set<User> observers = new HashSet<>();

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
        notifyObservers("A data inicial do evento mudou: " + initDate);
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
        notifyObservers("A data final do evento mudou: " + finalDate);
    }

    public void setLocal(String local) {
        this.local = local;
        notifyObservers("O local do evento mudou:  " + local);

    }

    public void setModality(String modality) {
        this.modality = modality;
        notifyObservers("A modalidade do evento mudou:  " + modality);

    }

    @Override
    public void addObserver(User observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(User observer) {
        observers.remove(observer);
    }


    @Override
    public void notifyObservers(String message) {
        Iterator<User> iterator = observers.iterator();
        while (iterator.hasNext()) {
            User observer = iterator.next();
            observer.update(new Notification(message, getLatestParticipation(observer)));
        }
    }

    public Participation getLatestParticipation(User observer) {
        return participations.stream()
                .filter(p -> p.getUser().equals(observer))
                .findFirst()
                .orElse(null);
    }

    public void loadObservers() {
        Set<User> newObservers = new HashSet<>();
        for (Participation participation : participations) {
            newObservers.add(participation.getUser());
        }
        observers.clear();
        observers.addAll(newObservers);
    }
}