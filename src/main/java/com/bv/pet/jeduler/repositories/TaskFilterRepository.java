package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

// TODO: change 'foo in :bars' to 'foo = any(values (bar1), ..., (barn))' --check values syntax
// TODO: add 'user_id = :user_id'
public interface TaskFilterRepository {
    String FILTER_BY_NAME =
            "select * from task t where UPPER(t.name) like CONCAT('%',UPPER(:name),'%')";
    String FILTER_BY_PRIORITY =
            "select * from task t where t.priority in :priorities";
//    String FILTER_BY_CATEGORIES =
//            "select t.id, t.name, t.description, t.task_done, t.priority,  from task t inner join task_category tc on t.id = tc.task_id " +
//                    "where tc.category_id in :category_ids";
    String FILTER_BY_FROM =
            "select * from task t where t.starts_at >= :from";
    String FILTER_BY_TO =
            "select * from task t where t.starts_at <= :to";

//    @Query(value = FILTER_BY_CATEGORIES, nativeQuery = true)
//    List<Integer> findByCategoryIds(@Param("category_ids") List<Short> categoryIds);
    @Query(value = FILTER_BY_NAME, nativeQuery = true)
    Page<Task> findByFirstNameLike(@Param("name") String name, Pageable pageable);
    @Query(value = FILTER_BY_PRIORITY, nativeQuery = true)
    Page<Task> findByPriority(@Param("priorities") List<Short> priorities, Pageable pageable);
    @Query(value = FILTER_BY_FROM, nativeQuery = true)
    Page<Task> findByFrom(@Param("from") Date from, Pageable pageable);
    @Query(value = FILTER_BY_TO, nativeQuery = true)
    Page<Task> findByTo(@Param("to") Date from, Pageable pageable);
}
