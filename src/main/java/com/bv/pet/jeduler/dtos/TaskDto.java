package com.bv.pet.jeduler.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
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

    private String description;

    @NotNull(message = "Set a date, please")
    private Instant startsAt;

    @NotNull(message = "Set a date, please")
    private Instant expiresAt;

    private Instant notifyAt;

    @Override
    public int compareTo(TaskDto o) {
        if (this.expiresAt.equals(o.expiresAt)){
            return 1;
        }

        return this.expiresAt.compareTo(o.expiresAt);
    }
}
