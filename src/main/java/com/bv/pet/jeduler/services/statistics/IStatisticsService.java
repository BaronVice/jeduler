package com.bv.pet.jeduler.services.statistics;

import com.bv.pet.jeduler.entities.Task;

public interface IStatisticsService {
    void onTaskCreation(Task task);
    void onTaskUpdate();
    void onSendingNotification();
    void incrementDayStatistics(Task task);
    void increment(StatisticsType type);
}
