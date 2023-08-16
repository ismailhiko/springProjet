package com.example.SpringH2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SpringH2.model.User;

/**
 * an interface that helps you use the jpaRepository's properties
 */
public interface UserRepo extends JpaRepository<User, String> {

    User findUserByUsername(String username);
}
