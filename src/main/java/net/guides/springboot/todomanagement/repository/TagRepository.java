package net.guides.springboot.todomanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.guides.springboot.todomanagement.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}

