package net.guides.springboot.todomanagement.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.service.ITodoService;
import net.guides.springboot.todomanagement.model.User;
import net.guides.springboot.todomanagement.service.UserService;
import net.guides.springboot.todomanagement.model.Priority;
import net.guides.springboot.todomanagement.model.Status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TodoController {

	@Autowired
	private ITodoService todoService;

	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date - dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String showTodos(@RequestParam(required = false) String search, ModelMap model) {
		User user = getLoggedInUser(model);
		Long userId = (user != null) ? user.getId() : null;
		// fetch all todos for user
		java.util.List<Todo> todos = (userId != null) ? todoService.getTodosByUserId(userId) : java.util.Collections.emptyList();

		// filter by status if provided
		String statusFilter = model.get("statusFilter") != null ? model.get("statusFilter").toString() : null;
		if (statusFilter != null && !statusFilter.isEmpty() && userId != null) {
			try {
				Status status = Status.valueOf(statusFilter);
				todos = todoService.getTodosByUserIdAndStatus(userId, status);
			} catch (IllegalArgumentException e) {
				// invalid status, ignore
			}
		}

		// filter by priority if provided
		String priorityFilter = model.get("priorityFilter") != null ? model.get("priorityFilter").toString() : null;
		if (priorityFilter != null && !priorityFilter.isEmpty() && userId != null) {
			try {
				Priority priority = Priority.valueOf(priorityFilter);
				todos = todoService.getTodosByUserIdAndPriority(userId, priority);
			} catch (IllegalArgumentException e) {
				// invalid priority, ignore
			}
		}

		// filter by search (description) if provided
		if (search != null && !search.trim().isEmpty()) {
			String q = search.trim().toLowerCase();
			todos = todos.stream()
					.filter(t -> t.getDescription() != null && t.getDescription().toLowerCase().contains(q))
					.collect(Collectors.toList());
		}

		// build map todoId -> daysUntilDue (targetDate - today)
		Map<Long, Long> daysLeftMap = new HashMap<>();
		for (Todo t : todos) {
			if (t.getTargetDate() != null) {
				LocalDate target = t.getTargetDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), target);
				daysLeftMap.put(t.getId(), daysLeft);
			} else {
				daysLeftMap.put(t.getId(), null);
			}
		}

		model.put("todos", todos);
		model.put("search", search);
		model.put("statusFilter", statusFilter);
		model.put("priorityFilter", priorityFilter);
		model.put("statuses", Status.values());
		model.put("priorities", Priority.values());
		model.put("daysLeftMap", daysLeftMap);
		return "list-todos";
	}

	private User getLoggedInUser(ModelMap model) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return null;
		}
		Object principal = auth.getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = (principal != null) ? principal.toString() : null;
		}
		if (username == null) return null;
		return userService.findByUsername(username);
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		model.addAttribute("todo", new Todo());
		return "todo";
	}

	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam long id) {
		todoService.deleteTodo(id);
		// service.deleteTodo(id);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam long id, ModelMap model) {
		Todo todo = todoService.getTodoById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid todo id: " + id));
		model.put("todo", todo);
		return "todo";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		if (result.hasErrors()) {
			return "todo";
		}

		User user = getLoggedInUser(model);
		if (user != null) todo.setUser(user);
		todoService.updateTodo(todo);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		if (result.hasErrors()) {
			return "todo";
		}

		User user = getLoggedInUser(model);
		if (user != null) todo.setUser(user);
		todoService.saveTodo(todo);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/update-todo-status", method = RequestMethod.GET)
	public String updateTodoStatus(@RequestParam long id, @RequestParam String status) {
		try {
			Status s = Status.valueOf(status);
			todoService.updateTodoStatus(id, s);
		} catch (IllegalArgumentException e) {
			// ignore invalid status
		}
		return "redirect:/list-todos";
	}
}
