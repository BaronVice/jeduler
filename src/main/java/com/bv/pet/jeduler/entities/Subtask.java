package com.bv.pet.jeduler.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(
//        indexes = {
//                @Index(name = "task_id_idx", columnList = "task_id")
//        }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subtask implements Comparable<Subtask>, ApplicationEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "task_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "FK_TASK_IDS",
                    foreignKeyDefinition = "foreign key (task_id) references task(id) on delete cascade"
            )
    )
    private Task task;

    private String name;

    private boolean isCompleted;

    private short orderInList;

    @Override
    public int compareTo(Subtask o) {
        return this.orderInList - o.orderInList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask subtask)) return false;
        return isCompleted() == subtask.isCompleted() && getOrderInList() == subtask.getOrderInList() && Objects.equals(getId(), subtask.getId()) && Objects.equals(getTask(), subtask.getTask()) && Objects.equals(getName(), subtask.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), isCompleted(), getOrderInList());
    }
}
