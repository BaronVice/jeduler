package com.bv.pet.jeduler.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.TreeSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
// TODO: add more constrains
public class TaskDto implements Comparable<TaskDto> {
    private Long id;

    @NotNull(message = "Name cannot be empty")
    private String name;

    private TreeSet<CategoryDto> categories;

    private List<SubtaskDto> subtasks;

    private String description;

    @NotNull(message = "Set a date, please")
    private Instant startsAt;

    private Instant notifyAt;

    @Override
    public int compareTo(TaskDto o) {
        if (this.startsAt.equals(o.startsAt)){
            return 1;
        }

        return this.startsAt.compareTo(o.startsAt);
    }
}
