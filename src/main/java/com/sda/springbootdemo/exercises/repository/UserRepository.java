package com.sda.springbootdemo.exercises.repository;

import com.sda.springbootdemo.exercises.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findOneByUsername(String username);
    Boolean existsByUsername(String username);
}
