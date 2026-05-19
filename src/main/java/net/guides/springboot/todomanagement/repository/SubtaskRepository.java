package net.guides.springboot.todomanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.guides.springboot.todomanagement.model.Subtask;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
    List<Subtask> findByTodo_Id(Long todoId);
}

