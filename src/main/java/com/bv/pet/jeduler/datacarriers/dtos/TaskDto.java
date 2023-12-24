package com.bv.pet.jeduler.datacarriers.dtos;

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

    private String name;

    private String description;

    private boolean taskDone;

    private short priority;

    private List<CategoryDto> categories;

    private List<SubtaskDto> subtasks;

    private Instant startsAt;

    private Instant notifyAt;
}
