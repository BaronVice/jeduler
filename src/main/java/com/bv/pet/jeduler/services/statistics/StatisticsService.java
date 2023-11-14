package com.bv.pet.jeduler.services.statistics;

import com.bv.pet.jeduler.dtos.StatisticsDto;
import com.bv.pet.jeduler.entities.Statistics;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.entities.TasksAtDay;
import com.bv.pet.jeduler.repositories.StatisticsRepository;
import com.bv.pet.jeduler.repositories.TasksAtDayRepository;
import com.bv.pet.jeduler.utils.InstantsCalculator;
import com.bv.pet.jeduler.utils.locks.StatisticsRepositoryLock;
import com.bv.pet.jeduler.utils.locks.TasksAtDayRepositoryLock;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@AllArgsConstructor
public class StatisticsService implements IStatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final TasksAtDayRepository tasksAtDayRepository;
    private final StatisticsRepositoryLock statisticsRepositoryLock;
    private final TasksAtDayRepositoryLock tasksAtDayRepositoryLock;

    private final List<Short> tasksAtDay;
    private final AtomicLong tasksCreated;
    private final AtomicLong tasksUpdated;
    private final AtomicLong notificationsSent;


    @Override
    public StatisticsDto getStatistics() {
        return StatisticsDto.builder()
                .tasksAtDay(null)
                .tasksCreated(tasksCreated.get())
                .tasksUpdated(tasksUpdated.get())
                .notificationsSent(notificationsSent.get())
                .build();
    }

    @Override
    @Async
    public void onTaskCreation(Task task) {
        incrementCreatedTasks(task);
    }

    @Override
    @Async
    public void onTaskUpdate() {
        increment(
                tasksUpdated,
                StatisticsType.TASKS_UPDATED
        );
    }

    @Override
    @Async
    public void onSendingNotification() {
        increment(
                notificationsSent,
                StatisticsType.NOTIFICATIONS_SENT
        );
    }

    @Transactional
    public void incrementCreatedTasks(Task task) {
        tasksCreated.incrementAndGet();
        short pos = InstantsCalculator.getDaysFromStart(task.getStartsAt());
        short val = tasksAtDay.get(pos);
        tasksAtDay.set(pos, (short)(val + 1));

        tasksAtDayRepositoryLock.getLock().lock();
        try {
            TasksAtDay tasks = tasksAtDayRepository.findById(pos).get();
            tasks.setTasksCreated((short)(tasks.getTasksCreated() + 1));
            tasksAtDayRepository.save(tasks);
        } finally {
            tasksAtDayRepositoryLock.getLock().unlock();
        }
    }

    @Override
    @Transactional
    public void increment(AtomicLong value, StatisticsType type) {
        value.incrementAndGet();
        statisticsRepositoryLock.getLock().lock();
        try {
            Statistics statistics = statisticsRepository.findById((short) type.ordinal()).get();
            statistics.setAmount( statistics.getAmount() + 1 );
            statisticsRepository.save(statistics);
        } finally {
            statisticsRepositoryLock.getLock().unlock();
        }
    }
}
