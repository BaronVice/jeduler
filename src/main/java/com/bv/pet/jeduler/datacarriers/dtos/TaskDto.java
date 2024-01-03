package com.bv.pet.jeduler.datacarriers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.List;

public record TaskDto (
        Integer id,

        String name,

        String description,

        boolean taskDone,

        short priority,

        List<Short> categoryIds,

        List<SubtaskDto> subtasks,

        @JsonFormat(pattern = "dd.MM.yyyy+HH:mm", timezone = "UTC")
        Instant startsAt,

        @JsonFormat(pattern = "dd.MM.yyyy+HH:mm", timezone = "UTC")
        Instant notifyAt
){
}
