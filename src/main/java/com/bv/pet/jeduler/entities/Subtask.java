package com.bv.pet.jeduler.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "task_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "FK_SUBTASK_IDS",
                    foreignKeyDefinition = "foreign key (task_id) references task(id) on delete cascade"
            )
    )
    private Task task;

    private String name;

    private boolean isCompleted;

    private short orderInList;
}
