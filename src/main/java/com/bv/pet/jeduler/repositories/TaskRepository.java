package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.repositories.projections.user.UserIdCollector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer>, UserIdCollector {
    String GET_USER_IDS =
            "select user_id from task";

    String FILTER_BY_CATEGORIES =
            "select tc.task_id from task_category tc where tc.category_id in(:category_ids)";

    String FILTER_BY_NAME =
            "select * from task t where UPPER(t.name) like CONCAT('%',UPPER(:name),'%')";


    @Query(value = GET_USER_IDS, nativeQuery = true)
    List<Short> getUserIds();
    @Query(value = FILTER_BY_CATEGORIES, nativeQuery = true)
    List<Integer> findByCategoryIds(@Param("category_ids") List<Short> categoryIds);
    @Query(value = FILTER_BY_NAME, nativeQuery = true)
    Page<Task> findByFirstNameLike(@Param("name") String name, Pageable pageable);
}
