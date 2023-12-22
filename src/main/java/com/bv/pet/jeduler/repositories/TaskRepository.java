package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.repositories.projections.UserId;
import com.bv.pet.jeduler.repositories.projections.UserIdCollector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer>, UserIdCollector {
    List<Task> findByOrderByStartsAtAsc();
    List<UserId> findAllBy();
}
