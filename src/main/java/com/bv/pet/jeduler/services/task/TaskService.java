package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.controllers.task.OrderType;
import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.repositories.CategoryRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.services.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TaskMapper taskMapper;
    private final TaskServiceHandler handler;
    private final FilteringHandler filtering;
    private final StatisticsService statistics;
    private final ApplicationInfo applicationInfo;
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public TaskDto get(int id) {
        Task task = handler.get(id);
        task.setCategoryIds(
                categoryRepository.findIdsByTaskId(task.getId())
        );

        return taskMapper.toTaskDto(task);
    }

    @Override
    @Transactional
    public List<TaskDto> get(
            short userId,
            String name,
            List<Short> priorities,
            List<Short> categories,
            Date from,
            Date to,
            int page,
            OrderType order
    ) {
        List<Task> tasks = filtering.filter(
                userId,
                name,
                priorities,
                categories,
                from,
                to,
                page,
                order
        );

        List<TaskDto> taskDtoList = taskMapper.toTaskDtoList(tasks);
        handler.setCategoryIdsForTaskDto(taskDtoList);

        return taskDtoList;
    }

    @Override
    public Integer create(short userId, String mail, TaskDto taskDto) {
        Task task = handler.create(userId, mail, taskDto);

        applicationInfo.userInfoTasks().changeValue(
                userId,
                (short) 1
        );
//        statistics.onTaskCreation(task);

        return task.getId();
    }

    @Override
    public void update(short userId, String mail, TaskDto taskDto) {
//        Task updated = taskMapper.toTask(taskDto);
//        Task toUpdate = handler.get(taskDto.getId());
//        boolean wasDone = toUpdate.isTaskDone();
//        Instant previousDate = toUpdate.getStartsAt();

        handler.update(userId, mail, taskDto);
        // statistics.onTaskUpdate(updated, wasDone, previousDate);
    }

    @Override
    public void delete(short userId, Integer id) {
        // TODO: check if user owns task with such id
        handler.delete(userId, id);
        applicationInfo.userInfoTasks().changeValue(
                userId,
                (short) -1
        );
    }
}
