package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.controllers.task.OrderType;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.utils.SqlFormatter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilteringRepository {
    private static final int PAGE_SIZE = 10;
    @PersistenceContext
    private final EntityManager em;

    public List<Task> filterForFuckSake(
            short userId,
            String name,
            List<Short> priorities,
            List<Short> categories,
            Date from,
            Date to,
            int page,
            OrderType order
    ){
        if (priorities == null){
            priorities = List.of((short) 1, (short) 2, (short) 3);
        }
        if (name != null){
            name = "%" + name + "%";
        }

        if (categories == null){
            return filterWithoutCategories(
                userId,
                name,
                priorities,
                from,
                to,
                page,
                order
            );
        }

        Query categoryIdsQuery = em.createNativeQuery(
                String.format(
                        "select distinct t.id from Task t inner join task_category tc on t.id = tc.task_id " +
                                "where tc.category_id = %s",
                        SqlFormatter.wrapInAnyValues(categories)
                ),
                Integer.class
        );
        List<Integer> taskIds = categoryIdsQuery.getResultList();

        if (taskIds.size() == 0){
            return new ArrayList<>();
        }

        Query query = em.createNativeQuery(
                String.format(
                        "select * from Task t where " +
                                "t.user_id = :user_id and " +
                                "(cast(:name as text) is null or UPPER(t.name) like UPPER(:name)) and " +
                                "(cast(:from as date) is null or t.starts_at >= :from) and " +
                                "(cast(:to as date) is null or t.starts_at <= :to) and " +
                                "t.id = %s and " +
                                "t.priority = %s order by t.%s",
                        SqlFormatter.wrapInAnyValues(taskIds),
                        SqlFormatter.wrapInAnyValues(priorities),
                        order.toString()
                ),
                Task.class
        );

        query.setParameter("user_id", userId);
        query.setParameter("name", name);
        query.setParameter("from", from);
        query.setParameter("to", to);
        setPaging(query, page);

        return query.getResultList();
    }

    private List<Task> filterWithoutCategories(
            short userId,
            String name,
            List<Short> priorities,
            Date from,
            Date to,
            int page,
            OrderType order
    ) {
        Query query = em.createNativeQuery(
                String.format(
                        "select * from Task t where " +
                                "t.user_id = :user_id and " +
                                "(cast(:name as text) is null or UPPER(t.name) like UPPER(:name)) and " +
                                "(cast(:from as date) is null or t.starts_at >= :from) and " +
                                "(cast(:to as date) is null or t.starts_at <= :to) and " +
                                "t.priority = %s order by t.%s",
                        SqlFormatter.wrapInAnyValues(priorities),
                        order.toString()
                ),
                Task.class
        );

        query.setParameter("user_id", userId);
        query.setParameter("name", name);
        query.setParameter("from", from);
        query.setParameter("to", to);
        setPaging(query, page);

        return query.getResultList();
    }

    private void setPaging(
            Query query,
            int page
    ){
        query.setFirstResult(page * PAGE_SIZE);
        query.setMaxResults(PAGE_SIZE);
    }
}
