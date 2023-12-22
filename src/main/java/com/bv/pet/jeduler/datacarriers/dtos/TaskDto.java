package com.bv.pet.jeduler.datacarriers.dtos;

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
public class TaskDto {
    private Integer id;

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
