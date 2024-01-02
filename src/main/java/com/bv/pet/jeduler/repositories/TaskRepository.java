package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.repositories.projections.user.UserIdCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer>, UserIdCollector {
    String GET_USER_IDS =
            "select user_id from task";

    @Query(value = GET_USER_IDS, nativeQuery = true)
    List<Short> getUserIds();
}
