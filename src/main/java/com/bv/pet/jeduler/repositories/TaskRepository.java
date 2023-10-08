package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
