package com.bv.pet.jeduler.mappers;

import com.bv.pet.jeduler.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Subtask;
import com.bv.pet.jeduler.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final CategoryMapper categoryMapper;
    private final SubtaskMapper subtaskMapper;

    public Task toTask(TaskDto taskDto){
        if ( taskDto == null ) {
            return null;
        }

        Task task = new Task();

        task.setId( taskDto.getId() );
        task.setName( taskDto.getName() );
        task.setDescription( taskDto.getDescription() );
        task.setStartsAt( taskDto.getStartsAt() );

        if ( taskDto.getCategories() != null ) {
            task.setCategories( categoryMapper.toCategoryList(
                    taskDto.getCategories().stream().toList()
            ));
        }
        if ( taskDto.getSubtasks() != null ) {
            task.setSubtasks( subtaskMapper.toSubtaskList(
                    taskDto.getSubtasks()
            ));
        }
        Instant notifyAt = taskDto.getNotifyAt();
        if ( notifyAt != null ){
            Notification notification = Notification
                    .builder()
                    .notifyAt( notifyAt )
                    .build();

            task.setNotification(notification);
        }

        return task;
    }

    public TaskDto toTaskDto(Task task){
        if ( task == null ) {
            return null;
        }

        TaskDto taskDto = new TaskDto();

        taskDto.setId( task.getId() );
        taskDto.setName( task.getName() );
        taskDto.setDescription( task.getDescription() );
        taskDto.setStartsAt( task.getStartsAt() );

        List<Category> categories = task.getCategories();
        if ( categories != null ) {
            taskDto.setCategories(
                    new TreeSet<>(categoryMapper.toCategoryDtoList(categories))
            );
        }
        List<Subtask> subtasks = task.getSubtasks();
        if ( subtasks != null ){
            taskDto.setSubtasks(
                    new LinkedList<>(subtaskMapper.toSubtaskDtoList(subtasks))
            );
        }

        Notification notification = task.getNotification();
        if ( notification != null ) {
            taskDto.setNotifyAt(notification.getNotifyAt());
        }

        return taskDto;
    }

    public List<Task> toTaskList(List<TaskDto> taskDtoList){
        if ( taskDtoList == null ) {
            return null;
        }

        List<Task> list = new ArrayList<>( taskDtoList.size() );
        for ( TaskDto taskDto : taskDtoList ) {
            list.add( toTask( taskDto ) );
        }

        return list;
    }

    public List<TaskDto> toTaskDtoList(List<Task> taskList){
        if ( taskList == null ) {
            return null;
        }

        List<TaskDto> list = new ArrayList<>( taskList.size() );
        for ( Task task : taskList ) {
            list.add( toTaskDto( task ) );
        }

        return list;
    }
}
