package com.bv.pet.jeduler.mappers;

import com.bv.pet.jeduler.datacarriers.dtos.SubtaskDto;
import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Subtask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SubtaskMapper {
    SubtaskDto toSubtaskDto(Subtask subtask);
    Subtask toSubtask(SubtaskDto subtaskDto);
    List<SubtaskDto> toSubtaskDtoList(List<Subtask> subtaskList);
    List<Subtask> toSubtaskList(List<SubtaskDto> subtaskDtoList);


}
