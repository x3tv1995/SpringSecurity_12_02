package ru.lesson.springsecurity_12_02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lesson.springsecurity_12_02.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
