package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtaskRepository extends JpaRepository<Subtask, Integer> {
}
