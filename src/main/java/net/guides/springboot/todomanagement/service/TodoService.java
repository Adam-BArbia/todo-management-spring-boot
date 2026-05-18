package net.guides.springboot.todomanagement.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.repository.TodoRepository;
import net.guides.springboot.todomanagement.model.User;
import net.guides.springboot.todomanagement.service.UserService;
import net.guides.springboot.todomanagement.model.Priority;
import net.guides.springboot.todomanagement.model.Status;

@Service
public class TodoService implements ITodoService {

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private UserService userService;

	@Override
	public List<Todo> getTodosByUserId(Long userId) {
		return todoRepository.findByUser_Id(userId);
	}

	@Override
	public Optional<Todo> getTodoById(long id) {
		return todoRepository.findById(id);
	}

	@Override
	public void updateTodo(Todo todo) {
		todoRepository.save(todo);
	}

	@Override
	public void addTodo(Long userId, String desc, Date targetDate, boolean isDone) {
		if (userId == null) return;
		User user = userService.findById(userId);
		if (user == null) return;
		todoRepository.save(new Todo(user, desc, targetDate));
	}

	@Override
	public void deleteTodo(long id) {
		Optional<Todo> todo = todoRepository.findById(id);
		if (todo.isPresent()) {
			todoRepository.delete(todo.get());
		}
	}

	@Override
	public void saveTodo(Todo todo) {
		todoRepository.save(todo);
	}

	public long getDaysUntilDue(Date targetDate) {
		if (targetDate == null) return -1;
		long diff = targetDate.getTime() - System.currentTimeMillis();
		return diff / (1000 * 60 * 60 * 24);
	}

	public List<Todo> getTodosByUserIdAndStatus(Long userId, Status status) {
		return todoRepository.findByUser_IdAndStatus(userId, status);
	}

	public List<Todo> getTodosByUserIdAndPriority(Long userId, Priority priority) {
		return todoRepository.findByUser_IdAndPriority(userId, priority);
	}

	public void updateTodoStatus(Long todoId, Status status) {
		Optional<Todo> todo = todoRepository.findById(todoId);
		if (todo.isPresent()) {
			todo.get().setStatus(status);
			todoRepository.save(todo.get());
		}
	}
}