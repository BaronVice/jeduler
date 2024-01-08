package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.controllers.task.OrderType;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.utils.SqlFormatter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilteringRepository {
    private static final int PAGE_SIZE = 10;
    @PersistenceContext
    private final EntityManager em;

    public List<Task> filterByName(
            String name,
            int pageNumber,
            OrderType order
    ){
        Query query = em.createNativeQuery(
                String.format(
                        "select * from Task t where UPPER(t.name) like '%s' order by t.%s",
                        "%" + name.toUpperCase() + "%",
                        order.toString()
                ),
                Task.class
        );
        setPaging(query, pageNumber);

        return query.getResultList();
    }

    public List<Task> filterByPriorities(
            List<Short> priorities,
            int pageNumber,
            OrderType order
    ){
        Query query = em.createNativeQuery(
                String.format(
                        "select * from Task t where t.priority = %s order by t.%s",
                        SqlFormatter.wrapInAnyValues(priorities),
                        order.toString()
                ),
                Task.class
        );
        setPaging(query, pageNumber);

        return query.getResultList();
    }

    public List<Task> filterByCategories(
            List<Short> categories,
            int pageNumber,
            OrderType order
    ){
        Query query = em.createNativeQuery(
                String.format(
                        "select distinct t.id, t.name, t.description, t.task_done, t.priority, t.user_id, t.starts_at, " +
                                "t.last_changed from Task t inner join task_category tc on t.id = tc.task_id " +
                                "where tc.category_id = %s order by t.%s",
                        SqlFormatter.wrapInAnyValues(categories),
                        order.toString()
                ),
                Task.class
        );
        setPaging(query, pageNumber);

        return query.getResultList();
    }

    public List<Task> filterByFrom(
            Date from,
            int pageNumber,
            OrderType order
    ){
        Query query = em.createNativeQuery(
                String.format(
                        "select * from Task t where t.starts_at >= :from order by t.%s",
                        order.toString()
                ),
                Task.class
        );
        query.setParameter("from", from);
        setPaging(query, pageNumber);

        return query.getResultList();
    }

    public List<Task> filterByTo(
            Date to,
            int pageNumber,
            OrderType order
    ){
        Query query = em.createNativeQuery(
                String.format(
                        "select * from Task t where t.starts_at <= :to order by t.%s",
                        order.toString()
                ),
                Task.class
        );
        query.setParameter("to", to);
        setPaging(query, pageNumber);

        return query.getResultList();
    }

    private void setPaging(
            Query query,
            int pageNumber
    ){
        query.setFirstResult(pageNumber * PAGE_SIZE);
        query.setMaxResults(PAGE_SIZE);
    }
}
