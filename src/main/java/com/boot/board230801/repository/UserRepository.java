package com.boot.board230801.repository;

import com.boot.board230801.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
