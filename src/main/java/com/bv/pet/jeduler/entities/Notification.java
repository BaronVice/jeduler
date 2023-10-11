package com.bv.pet.jeduler.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification implements Comparable<Notification> {
    @Id
    @Column(name = "task_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private Task task;

    private Instant notifyAt;

    @Override
    public int compareTo(Notification o) {
        if (this.notifyAt.equals(o.notifyAt)){
            return 1;
        }
        return this.notifyAt.compareTo(o.notifyAt);
    }
}
