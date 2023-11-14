package com.bv.pet.jeduler.services.statistics;

import com.bv.pet.jeduler.dtos.StatisticsDto;
import com.bv.pet.jeduler.entities.Task;

import java.util.concurrent.atomic.AtomicLong;

public interface IStatisticsService {
    StatisticsDto getStatistics();
    void onTaskCreation(Task task);
    void onTaskUpdate();
    void onSendingNotification();
    void increment(AtomicLong value, StatisticsType type);
}