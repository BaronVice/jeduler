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
public class Notification {
    @Id
    @Column(name = "task_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(
            name = "task_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "FK_NOTIFICATION_ID",
                    foreignKeyDefinition = "foreign key (task_id) references task(id) on delete cascade"
            )
    )
    @JsonIgnore
    private Task task;

    private Instant notifyAt;
}
