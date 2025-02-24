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
public class Event {
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

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public void setLocal(String local) {
        this.local = local;

    }

    public void setModality(String modality) {
        this.modality = modality;

    }

}