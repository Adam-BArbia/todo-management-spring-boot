package net.guides.springboot.todomanagement.service;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.model.Priority;
import net.guides.springboot.todomanagement.model.Status;

public interface ITodoService {

	List<Todo> getTodosByUserId(Long userId);

	Optional<Todo> getTodoById(long id);

	void updateTodo(Todo todo);

	void addTodo(Long userId, String desc, Date targetDate, boolean isDone);

	void deleteTodo(long id);
	
	void saveTodo(Todo todo);

	List<Todo> getTodosByUserIdAndStatus(Long userId, Status status);

	List<Todo> getTodosByUserIdAndPriority(Long userId, Priority priority);

	void updateTodoStatus(Long todoId, Status status);
}