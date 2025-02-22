package com.pds.my_events.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "participation")
@Data
@NoArgsConstructor
@Component
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private LocalDate registrationDate = LocalDate.now();

    public Participation(User user, Event event) {
        this.user = user;
        this.event = event;
        this.registrationDate = LocalDate.now();
    }



}
