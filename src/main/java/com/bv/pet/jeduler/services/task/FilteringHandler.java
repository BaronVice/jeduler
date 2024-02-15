package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.controllers.task.OrderType;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.repositories.FilteringRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FilteringHandler {
    private final TaskRepository taskRepository;
    private final FilteringRepository filteringRepository;

    public List<Task> filter(
            short userId,
            String name,
            List<Short> priorities,
            List<Short> categories,
            Date from,
            Date to,
            int page,
            OrderType order
    ) {
        List<Task> tasks = filteringRepository.filterForFuckSake(
                userId,
                name,
                priorities,
                categories,
                from,
                to,
                page,
                order
        );

        tasks.forEach(t -> t.getSubtasks().forEach(st -> System.out.println(st.getOrderInList())));

        return tasks;
    }
}
