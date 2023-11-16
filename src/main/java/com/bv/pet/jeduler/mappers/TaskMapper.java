package com.bv.pet.jeduler.mappers;

import com.bv.pet.jeduler.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toTaskDto(Task task);
    Task toTask(TaskDto taskDto);
    List<TaskDto> toTaskDtoList(List<Task> taskList);
    List<Task> toTaskList(List<TaskDto> taskDtoList);
}
