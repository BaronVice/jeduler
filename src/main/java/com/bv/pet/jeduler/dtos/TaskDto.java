package com.bv.pet.jeduler.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
// TODO: add more constrains
public class TaskDto {
    private Long id;

    @NotNull(message = "Name cannot be empty")
    private String name;

    private String description;

    private boolean taskDone;

    private List<CategoryDto> categories;

    private List<SubtaskDto> subtasks;

    @NotNull(message = "Set a date, please")
    private Instant startsAt;

    private Instant notifyAt;
}
