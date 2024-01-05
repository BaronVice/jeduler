package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.repositories.projections.task.TaskCategory;
import com.bv.pet.jeduler.repositories.projections.user.UserIdCollector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, UserIdCollector, TaskFilterRepository {
    String GET_USER_IDS =
            "select user_id from task";

    String GET_CATEGORY_IDS_BY_TASK_IDS =
            "select task_id, category_id from task_category where task_id in :task_ids";

    @Query(value = GET_USER_IDS, nativeQuery = true)
    List<Short> getUserIds();
    @Query(value = GET_CATEGORY_IDS_BY_TASK_IDS, nativeQuery = true)
    List<TaskCategory> getCategoryIdsByTaskIds(@Param("task_ids") List<Integer> taskIds);
}
