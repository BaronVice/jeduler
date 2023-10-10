package com.bv.pet.jeduler.dtos;

import com.bv.pet.jeduler.entities.Category;
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

    private String description;

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
