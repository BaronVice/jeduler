package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Short> {
    Optional<User> findByUsername(String username);
}
