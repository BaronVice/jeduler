package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Short> {
    String ALL_ID = "select id from users";

    Optional<User> findByUsername(String username);
    @Query(value = ALL_ID, nativeQuery = true)
    List<Short> findIds();
}
