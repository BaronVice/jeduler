package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilteringHandler {
    private final TaskRepository taskRepository;

    public List<Task> filter(
            Optional<String> name,
            Optional<List<Short>> priority,
            Optional<List<Short>> categories,
            Optional<Date> from,
            Optional<Date> to,
            Pageable pageable
    ) {
        // TODO: 5 + 4 + 3 + 2 + 1 = 15 combinations => I have to hardcode 15 queries :skull:
//        System.out.println(name.isPresent());
//        System.out.println(priority.isPresent());
//        System.out.println(categories.isPresent());
//        System.out.println(from.isPresent());
//        System.out.println(to.isPresent());

        name.ifPresent(n -> System.out.println(taskRepository.findByFirstNameLike(n, pageable).getTotalElements()));
        priority.ifPresent(p -> System.out.println(taskRepository.findByPriority(p, pageable).getTotalElements()));
        from.ifPresent(f -> System.out.println(taskRepository.findByFrom(f, pageable).getTotalElements()));
        to.ifPresent(t -> System.out.println(taskRepository.findByTo(t, pageable).getTotalElements()));

//        taskRepository.findByFirstNameLike(name, pageable).getContent();
//        tasks.forEach(t -> t.setCategoryIds(new ArrayList<>()));
        return new ArrayList<>();
    }
}
