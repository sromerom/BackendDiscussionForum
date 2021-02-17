package com.liceu.sromerom.discussionforum.repos;

import com.liceu.sromerom.discussionforum.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
