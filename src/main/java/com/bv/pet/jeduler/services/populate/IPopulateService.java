package com.bv.pet.jeduler.services.populate;

import java.util.Date;
import java.util.List;

public interface IPopulateService {
    void addUsers(int amount, int categoriesPerUser, int tasksPerUser, int subtasksPerTask);
    void addTasks(short userId, int amount, int subtasksPerTask);
    void addCategories(int amount, List<Short> userIds, List<Integer> taskIds);
    void addSubtasks(int taskId, int amount);
    void addNotification(int taskId, Date date);
}
