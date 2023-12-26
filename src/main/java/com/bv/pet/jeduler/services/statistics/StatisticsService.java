package com.bv.pet.jeduler.services.statistics;

import com.bv.pet.jeduler.datacarriers.dtos.StatisticsDto;
import com.bv.pet.jeduler.entities.Statistics;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.entities.TasksAtDay;
import com.bv.pet.jeduler.repositories.StatisticsRepository;
import com.bv.pet.jeduler.repositories.TasksAtDayRepository;
import com.bv.pet.jeduler.utils.locks.StatisticsRepositoryLock;
import com.bv.pet.jeduler.utils.locks.TasksAtDayRepositoryLock;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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
                .tasksAtDay(tasksAtDay)
                .tasksCompleted(tasksAtDay.stream().mapToLong(e->e).sum())
                .tasksCreated(tasksCreated.get())
                .tasksUpdated(tasksUpdated.get())
                .notificationsSent(notificationsSent.get())
                .build();
    }

    @Override
    @Async
    public void onTaskCreation(Task task) {
        increment(
                tasksCreated,
                StatisticsType.TASKS_CREATED
        );
    }

    @Override
    @Async
    public void onTaskUpdate(Task updated, boolean wasDone, Instant previousDate) {
//        increment(
//                tasksUpdated,
//                StatisticsType.TASKS_UPDATED
//        );
//
//        if (updated.isTaskDone() && (!wasDone))
//            changeTasksAtDay(updated.getStartsAt(), (short) 1);
//        if (!updated.isTaskDone() && wasDone)
//            changeTasksAtDay(previousDate, (short) -1);
//        if (updated.isTaskDone() && wasDone && (!updated.getStartsAt().equals(previousDate))){
//            changeTasksAtDay(previousDate, (short) -1);
//            changeTasksAtDay(updated.getStartsAt(), (short) 1);
//        }
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
    public void changeTasksAtDay(Instant instant, short v) {
        short pos = /*InstantsCalculator.getDaysFromStart(instant)*/1;
        short val = tasksAtDay.get(pos);
        tasksAtDay.set(pos, (short)(val + v));

        tasksAtDayRepositoryLock.getLock().lock();
        try {
            TasksAtDay tasks = tasksAtDayRepository.findById(pos).get();
            tasks.setTasksCreated((short)(tasks.getTasksCreated() + v));
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
