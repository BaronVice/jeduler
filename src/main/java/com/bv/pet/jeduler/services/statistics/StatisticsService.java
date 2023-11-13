package com.bv.pet.jeduler.services.statistics;

import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.repositories.StatisticsRepository;
import com.bv.pet.jeduler.repositories.TasksAtDayRepository;
import com.bv.pet.jeduler.utils.locks.StatisticsRepositoryLock;
import com.bv.pet.jeduler.utils.locks.TasksAtDayRepositoryLock;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StatisticsService implements IStatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final TasksAtDayRepository tasksAtDayRepository;
    private final StatisticsRepositoryLock statisticsRepositoryLock;
    private final TasksAtDayRepositoryLock tasksAtDayRepositoryLock;


    @Async
    @Override
    public void onTaskCreation(Task task) {
        tasksAtDayRepositoryLock.getLock().lock();
        try {
            incrementDayStatistics(task);
        } finally {
            tasksAtDayRepositoryLock.getLock().unlock();
        }
    }

    @Override
    @Async
    public void onTaskUpdate() {
        // TODO: increment var and table row
    }

    @Override
    public void onSendingNotification() {
        // TODO: increment var and table row
    }

    @Transactional
    @Override
    public void incrementDayStatistics(Task task) {
        // TODO: get day in table by date, then add in both table and list(implement it somewhere)
    }

    @Override
    public void increment(StatisticsType type) {

    }
}
