package com.bv.pet.jeduler.services.statistics;

import com.bv.pet.jeduler.dtos.StatisticsDto;
import com.bv.pet.jeduler.entities.Statistics;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.repositories.StatisticsRepository;
import com.bv.pet.jeduler.repositories.TasksAtDayRepository;
import com.bv.pet.jeduler.utils.locks.StatisticsRepositoryLock;
import com.bv.pet.jeduler.utils.locks.TasksAtDayRepositoryLock;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

@Service
@AllArgsConstructor
public class StatisticsService implements IStatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final TasksAtDayRepository tasksAtDayRepository;
    private final StatisticsRepositoryLock statisticsRepositoryLock;
    private final TasksAtDayRepositoryLock tasksAtDayRepositoryLock;
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
        // TODO
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

    @Override
    @Transactional
    public void incrementDayStatistics(Task task) {
        // TODO: get day in table by date, then add in both table and list(implement it somewhere)
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
