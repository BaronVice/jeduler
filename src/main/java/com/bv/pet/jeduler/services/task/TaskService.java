package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.controllers.task.OrderType;
import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.repositories.CategoryRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.repositories.projections.task.TaskCategory;
import com.bv.pet.jeduler.services.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


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
    @Transactional(readOnly = true)
    // TODO: the amount of queries is much worse than terrible
    public List<TaskDto> get(List<Short> categoryIds) {
//        if (categoryIds.size() == 0){
//            categoryIds.add((short) 0);
//        }
//        List<Integer> taskIds = taskRepository.findByCategoryIds(categoryIds);
//        List<Task> tasks = taskRepository.findAllById(taskIds);
//        for (Task task : tasks){
//            task.setCategoryIds(
//                    categoryRepository.findIdsByTaskId(task.getId())
//            );
//        }
//
//        return taskMapper.toTaskDtoList(tasks);
        return null;
    }

    @Override
    @Transactional
    public List<TaskDto> get(
            Optional<String> name,
            Optional<List<Short>> priorities,
            Optional<List<Short>> categories,
            Optional<Date> from,
            Optional<Date> to,
            int page,
            OrderType orderType
    ) {
        List<Task> tasks = filtering.filter(
                name,
                priorities,
                categories,
                from,
                to,
                PageRequest.of(page, 10, Sort.by(orderType.toString()))
        );

        List<TaskDto> taskDtoList = taskMapper.toTaskDtoList(tasks);
        handler.setCategoryIdsForTaskDto(taskDtoList);

        return taskDtoList;
    }

    @Override
    public Integer create(short userId, String mail, TaskDto taskDto) {
        Task task = handler.create(userId, mail, taskDto);

        applicationInfo.userInfoTasks().changeValue(userId, (short) 1);
//        statistics.onTaskCreation(task);

        return task.getId();
    }

    @Override
    public void update(String mail, TaskDto taskDto) {
//        Task updated = taskMapper.toTask(taskDto);
//        Task toUpdate = handler.get(taskDto.getId());
//        boolean wasDone = toUpdate.isTaskDone();
//        Instant previousDate = toUpdate.getStartsAt();

        handler.update(mail, taskDto);
        // statistics.onTaskUpdate(updated, wasDone, previousDate);
    }

    @Override
    public void delete(short userId, Integer id) {
        handler.delete(id);
        applicationInfo.userInfoTasks().changeValue(
                userId,
                (short) -1
        );
    }
}
