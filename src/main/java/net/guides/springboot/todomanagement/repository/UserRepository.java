package net.guides.springboot.todomanagement.repository;

import net.guides.springboot.todomanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}