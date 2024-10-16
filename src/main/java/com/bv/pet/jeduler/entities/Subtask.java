package com.bv.pet.jeduler.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(
        indexes = {
                @Index(name = "task_id_idx", columnList = "task_id")
        }
)
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
        return getIsCompleted() == subtask.getIsCompleted() && getOrderInList() == subtask.getOrderInList() && Objects.equals(getId(), subtask.getId()) && Objects.equals(getTask(), subtask.getTask()) && Objects.equals(getName(), subtask.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getIsCompleted(), getOrderInList());
    }

    public Integer getId() {
        return this.id;
    }

    public Task getTask() {
        return this.task;
    }

    public String getName() {
        return this.name;
    }

    public boolean getIsCompleted() {
        return this.isCompleted;
    }

    public short getOrderInList() {
        return this.orderInList;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setOrderInList(short orderInList) {
        this.orderInList = orderInList;
    }
}
