package com.bv.pet.jeduler.datacarriers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "dd.MM.yyyy+HH:mm", timezone = "UTC")
    private Instant startsAt;

    @JsonFormat(pattern = "dd.MM.yyyy+HH:mm", timezone = "UTC")
    private Instant notifyAt;
}
