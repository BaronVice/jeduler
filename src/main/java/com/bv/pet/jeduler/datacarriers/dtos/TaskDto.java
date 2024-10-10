package com.bv.pet.jeduler.datacarriers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;

public record TaskDto (
        Integer id,

        String name,

        String description,

        boolean taskDone,

        short priority,

        List<Short> categoryIds,

        @Size(max = 20, message = "Subtasks limit is 20")
        List<SubtaskDto> subtasks,

        @JsonFormat(pattern = "dd.MM.yyyy-HH:mm", timezone = "UTC")
        Instant startsAt,

        @JsonFormat(pattern = "dd.MM.yyyy-HH:mm", timezone = "UTC")
        Instant notifyAt
){
}
