package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Integer> {
    String GET_SUBTASKS_BY_TASK_IDS =
            "select st from subtask st where st.task_id in :ids";
}
