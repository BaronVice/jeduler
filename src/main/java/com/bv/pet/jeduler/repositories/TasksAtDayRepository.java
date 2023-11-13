package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.TasksAtDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksAtDayRepository extends JpaRepository<TasksAtDay, Short> {
}
