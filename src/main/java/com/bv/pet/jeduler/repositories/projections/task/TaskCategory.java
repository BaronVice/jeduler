package com.bv.pet.jeduler.repositories.projections.task;

import org.springframework.beans.factory.annotation.Value;

public interface TaskCategory {
    @Value("#{target.task_id}")
    int getTaskId();
    @Value("#{target.category_id}")
    short getCategoryId();
}
