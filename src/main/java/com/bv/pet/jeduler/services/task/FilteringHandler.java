package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.controllers.task.OrderType;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.repositories.FilteringRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FilteringHandler {
    private final TaskRepository taskRepository;
    private final FilteringRepository filteringRepository;

    public List<Task> filter(
            Optional<String> name,
            Optional<List<Short>> priorities,
            Optional<List<Short>> categories,
            Optional<Date> from,
            Optional<Date> to,
            int page,
            OrderType order
    ) {
        // TODO: 5 + 4 + 3 + 2 + 1 = 15 combinations => I have to hardcode 15 queries :skull:
//        System.out.println(name.isPresent());
//        System.out.println(categories.isPresent());
//        System.out.println(from.isPresent());
//        System.out.println(to.isPresent());
        priorities.ifPresent(p -> System.out.println(filteringRepository.filterByPriorities(p, page, order).stream().map(Task::getId).collect(Collectors.toList())));
        name.ifPresent(n -> System.out.println(filteringRepository.filterByName(n, page, order).stream().map(Task::getId).collect(Collectors.toList())));
        categories.ifPresent(c -> System.out.println(filteringRepository.filterByCategories(c, page, order).stream().map(Task::getId).collect(Collectors.toList())));
        from.ifPresent(f -> System.out.println(filteringRepository.filterByFrom(f, page, order).stream().map(Task::getId).collect(Collectors.toList())));
        to.ifPresent(t -> System.out.println(filteringRepository.filterByTo(t, page, order).stream().map(Task::getId).collect(Collectors.toList())));


//        from.ifPresent(f -> System.out.println(taskRepository.findByFrom(f, pageable).getTotalElements()));
//        to.ifPresent(t -> System.out.println(taskRepository.findByTo(t, pageable).getTotalElements()));
//        name.ifPresent(n -> System.out.println(taskRepository.findByFirstNameLike(n, pageable).getTotalElements()));

//        taskRepository.findByFirstNameLike(name, pageable).getContent();
//        tasks.forEach(t -> t.setCategoryIds(new ArrayList<>()));
        return new ArrayList<>();
    }
}
