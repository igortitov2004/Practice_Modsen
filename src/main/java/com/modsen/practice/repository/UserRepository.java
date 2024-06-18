package com.modsen.practice.repository;

import com.modsen.practice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
        User findByLogin(String login);

        User findByEmail(String email);
}
