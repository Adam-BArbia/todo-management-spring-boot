package net.guides.springboot.todomanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.model.Priority;
import net.guides.springboot.todomanagement.model.Status;

public interface TodoRepository extends JpaRepository<Todo, Long>{
	// find todos by owner's id (user_id FK)
	List<Todo> findByUser_Id(Long userId);

	List<Todo> findByUser_IdAndStatus(Long userId, Status status);

	List<Todo> findByUser_IdAndPriority(Long userId, Priority priority);
}
