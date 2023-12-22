package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.repositories.projections.UserId;
import com.bv.pet.jeduler.repositories.projections.UserIdCollector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Short>, UserIdCollector {
    List<Category> findByUserIdOrderByNameAsc(short userId);
    List<UserId> findAllBy();
}
