package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.repositories.projections.user.UserId;
import com.bv.pet.jeduler.repositories.projections.user.UserIdCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Short>, UserIdCollector {
    /*
       --- This method returns ids without sort by names. One of the solution - join table, nut that's kinda bad ---
       ... so let it be ...
     */
    String GET_CATEGORY_IDS_BY_TASK_ID =
            "select category_id from task_category where task_id = :task_id";
    String GET_CATEGORY_NAMES_BY_TASK_ID =
            "select ca.name from category ca inner join task_category tc " +
                    "on ca.id = tc.category_id where task_id = :task_id " +
                        "order by ca.name asc";

    List<Category> findByUserIdOrderByNameAsc(short userId);
    List<UserId> findAllBy();
    @Query(value = GET_CATEGORY_IDS_BY_TASK_ID, nativeQuery = true)
    List<Short> findIdsByTaskId(@Param("task_id") int taskId);
    @Query(value = GET_CATEGORY_NAMES_BY_TASK_ID, nativeQuery = true)
    List<String> findNamesByTaskId(@Param("task_id") int taskId);
}
