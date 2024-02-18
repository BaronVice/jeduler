package com.bv.pet.jeduler.services.mock;

import java.util.Date;
import java.util.List;

public interface IMockService {
    void addUsers(int amount);
    void addTasks(int amount, short userId);
    void addCategories(int amount, short userId);
    void addNotification(Date date);
}
