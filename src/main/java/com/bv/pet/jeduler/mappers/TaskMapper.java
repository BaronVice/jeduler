package com.bv.pet.jeduler.mappers;

import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "notification", target = "notifyAt", qualifiedByName = "notificationToInstant")
    @Mapping(target = "categoryIds", defaultExpression = "java(new ArrayList())")
    TaskDto toTaskDto(Task task);
    @Mapping(source = "notifyAt", target = "notification", qualifiedByName = "instantToNotification")
    @Mapping(source = "categoryIds", target = "categories", qualifiedByName = "categoryIdsToCategories")
    Task toTask(TaskDto taskDto);
    List<TaskDto> toTaskDtoList(List<Task> taskList);
    List<Task> toTaskList(List<TaskDto> taskDtoList);

    @Named("notificationToInstant")
    static Instant notificationToInstant(Notification notification){
        return (notification == null ? null : notification.getNotifyAt());
    }

    @Named("instantToNotification")
    static Notification instantToNotification(Instant instant){
        return (instant == null ? null : Notification.builder().notifyAt(instant).build());
    }

    @Named("categoryIdsToCategories")
    static List<Category> categoryIdsToCategories(List<Short> categoryIds){
        return categoryIds.stream().map(
                id -> Category.builder().id(id).build()
        ).collect(Collectors.toList());
    }
}
