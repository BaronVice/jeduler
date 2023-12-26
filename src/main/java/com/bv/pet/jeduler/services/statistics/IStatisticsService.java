package com.bv.pet.jeduler.services.statistics;

import com.bv.pet.jeduler.datacarriers.dtos.StatisticsDto;
import com.bv.pet.jeduler.entities.Task;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public interface IStatisticsService {
    StatisticsDto getStatistics();
    void onTaskCreation(Task task);
    void onTaskUpdate(Task updated, boolean wasDone, Instant previousDate);
    void onSendingNotification();
    void increment(AtomicLong value, StatisticsType type);
}
