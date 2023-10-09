package com.bv.pet.jeduler.mappers;

import com.bv.pet.jeduler.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TaskMapper {
    public static Task toTask(TaskDto taskDto){
        if ( taskDto == null ) {
            return null;
        }

        Task task = new Task();

        task.setId( taskDto.getId() );
        task.setName( taskDto.getName() );
        task.setDescription( taskDto.getDescription() );
        task.setStartsAt( taskDto.getStartsAt() );
        task.setExpiresAt( taskDto.getExpiresAt() );

        List<Category> list = taskDto.getCategories();
        if ( list != null ) {
            task.setCategories( new ArrayList<>( list ) );
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

    public static TaskDto toTaskDto(Task task){
        if ( task == null ) {
            return null;
        }

        TaskDto taskDto = new TaskDto();

        taskDto.setId( task.getId() );
        taskDto.setName( task.getName() );
        taskDto.setDescription( task.getDescription() );
        taskDto.setStartsAt( task.getStartsAt() );
        taskDto.setExpiresAt( task.getExpiresAt() );

        List<Category> list = task.getCategories();
        if ( list != null ) {
            taskDto.setCategories(new ArrayList<>(list));
        }
        Notification notification = task.getNotification();
        if ( notification != null ) {
            taskDto.setNotifyAt(notification.getNotifyAt());
        }

        return taskDto;
    }

    public static List<Task> toTaskList(List<TaskDto> taskDtoList){
        if ( taskDtoList == null ) {
            return null;
        }

        List<Task> list = new ArrayList<>( taskDtoList.size() );
        for ( TaskDto taskDto : taskDtoList ) {
            list.add( toTask( taskDto ) );
        }

        return list;
    }

    public static List<TaskDto> toTaskDtoList(List<Task> taskList){
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
