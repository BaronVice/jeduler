package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Short> {
}
