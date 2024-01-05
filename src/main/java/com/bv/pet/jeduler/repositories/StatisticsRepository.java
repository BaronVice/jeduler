package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Short> {
}
