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
public class TaskService {
    private final TaskServiceHandler handler;
    private final FilteringHandler filtering;
    private final StatisticsService statistics;
    private final ApplicationInfo applicationInfo;

    @Transactional(readOnly = true)
    public TaskDto get(short userId, int id) {
        return handler.get(userId, id);
    }

    @Transactional
    public List<TaskDto> get(
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
        return filtering.get(
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
        );
    }

    public Integer create(short userId, TaskDto taskDto) {
        Task task = handler.create(userId, taskDto);

        applicationInfo.userInfoTasks().changeValue(
                userId,
                (short) 1
        );
//        statistics.onTaskCreation(task);

        return task.getId();
    }

    public void update(short userId, TaskDto taskDto) {
//        Task updated = taskMapper.toTask(taskDto);
//        Task toUpdate = handler.get(taskDto.getId());
//        boolean wasDone = toUpdate.isTaskDone();
//        Instant previousDate = toUpdate.getStartsAt();

        handler.update(userId, taskDto);
        // statistics.onTaskUpdate(updated, wasDone, previousDate);
    }

    public void delete(short userId, Integer id) {
        handler.delete(userId, id);
        applicationInfo.userInfoTasks().changeValue(
                userId,
                (short) -1
        );
    }
}
