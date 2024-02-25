package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.controllers.task.OrderType;
import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.repositories.FilteringRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.repositories.projections.task.TaskCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FilteringHandler {
    private final TaskRepository taskRepository;
    private final FilteringRepository filteringRepository;
    private final TaskMapper taskMapper;

    public List<TaskDto> get (
            short userId,
            String name,
            List<Short> priorities,
            List<Short> categories,
            boolean categoriesAny,
            Date from,
            Date to,
            int page,
            int size,
            OrderType order
    ) {
        List<TaskDto> taskDtos = taskMapper.toTaskDtoList(
                filteringRepository.filter(
                        userId,
                        name,
                        priorities,
                        categories,
                        categoriesAny,
                        from,
                        to,
                        page,
                        size,
                        order
                )
        );
        setCategoryIdsForTaskDto(taskDtos);

        return taskDtos;
    }

    public void setCategoryIdsForTaskDto(List<TaskDto> taskDtoList) {
        if (taskDtoList.size() == 0) return;

        // TODO: is there a better way to collect them?
        Map<Integer, TaskDto> map = taskDtoList.stream().collect(Collectors.toMap(TaskDto::id, Function.identity()));
        List<TaskCategory> taskCategories = taskRepository.getCategoryIdsByTaskIds(
                taskDtoList.stream().map(TaskDto::id).toList()
        );

        for (TaskCategory taskCategory : taskCategories)
            map.get(taskCategory.getTaskId()).categoryIds().add(taskCategory.getCategoryId());
    }
}
