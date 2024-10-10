package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.controllers.task.OrderType;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.utils.SqlFormatter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilteringRepository {
    @PersistenceContext
    private final EntityManager em;

    public List<Task> filter(
            short userId,
            String name,
            List<Short> priorities,
            List<Short> categories,
            boolean categoriesAny,
            String taskDone,
            Date from,
            Date to,
            int page,
            int size,
            OrderType order
    ){
        StringBuilder queryBuilder = new StringBuilder("select * from Task t where t.user_id = :user_id");
        filterFrom(queryBuilder, from);
        filterTo(queryBuilder, to);
        filterPriorities(queryBuilder, priorities);
        filterName(queryBuilder, name);
        filterCategories(queryBuilder, categories);
        queryBuilder.append(" order by :order");

        Query filterQuery = em.createNativeQuery(
                queryBuilder.toString(),
                Task.class
        );

        filterQuery.setParameter("user_id", userId);
        filterQuery.setParameter("order", order.toString());
        setParameter(filterQuery, "name", name);
        setParameter(filterQuery, "from", from);
        setParameter(filterQuery, "to", to);
        setPaging(filterQuery, page, size);

        return filterQuery.getResultList();
    }

    private void setParameter(Query query, String name, Object value){
        try {
            query.setParameter(name, value);
        } catch (IllegalArgumentException ignored){
        }
    }

    private void filterCategories(StringBuilder queryBuilder, List<Short> categories) {
        if (categories == null)
            return;

        Query categoryIdsQuery = em.createNativeQuery(
                String.format(
                        "select distinct t.id from Task t inner join task_category tc on t.id = tc.task_id " +
                                "where tc.category_id = %s",
                        SqlFormatter.wrapInAnyValues(categories)
                ),
                Integer.class
        );
        List<Integer> taskIds = categoryIdsQuery.getResultList();

        if (taskIds.size() == 0)
            return;

        queryBuilder.append(" and t.id = ").append(SqlFormatter.wrapInAnyValues(taskIds));
    }

    private void filterName(StringBuilder queryBuilder, String name) {
        if (name == null)
            return;

        queryBuilder.append(" and UPPER(t.name) like CONCAT('%',UPPER(:name),'%')");
    }

    private void filterPriorities(StringBuilder queryBuilder, List<Short> priorities) {
        if (priorities == null)
            return;

        queryBuilder.append(" and t.priority = ").append(SqlFormatter.wrapInAnyValues(priorities));
    }

    private void filterTo(StringBuilder queryBuilder, Date to) {
        if (to == null)
            return;

        queryBuilder.append(" and t.starts_at <= :to");
    }

    private void filterFrom(StringBuilder queryBuilder, Date from) {
        if (from == null)
            return;

        queryBuilder.append(" and t.starts_at >= :from");
    }

    private void setPaging(
            Query query,
            int page,
            int size
    ){
        query.setFirstResult(page * size);
        query.setMaxResults(size);
    }
}
