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

	@Autowired
	private net.guides.springboot.todomanagement.service.TagService tagService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date - dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String showTodos(@RequestParam(required = false) String search,
							@RequestParam(required = false) String statusFilter,
							@RequestParam(required = false) String priorityFilter,
							@RequestParam(required = false) String tagFilter,
							ModelMap model) {
		User user = getLoggedInUser(model);
		Long userId = (user != null) ? user.getId() : null;
		// fetch all todos for user
		java.util.List<Todo> todos = (userId != null) ? todoService.getTodosByUserId(userId) : java.util.Collections.emptyList();

		// apply status and priority filters on the already-fetched list so multiple filters combine
		if (statusFilter != null && !statusFilter.isEmpty()) {
			try {
				Status status = Status.valueOf(statusFilter);
				todos = todos.stream()
						.filter(t -> t.getStatus() == status)
						.collect(Collectors.toList());
			} catch (IllegalArgumentException e) {
				// invalid status, ignore
			}
		}

		if (priorityFilter != null && !priorityFilter.isEmpty()) {
			try {
				Priority priority = Priority.valueOf(priorityFilter);
				todos = todos.stream()
						.filter(t -> t.getPriority() == priority)
						.collect(Collectors.toList());
			} catch (IllegalArgumentException e) {
				// invalid priority, ignore
			}
		}

		// filter by tag name (single tag) if provided
		if (tagFilter != null && !tagFilter.isEmpty()) {
			String tf = tagFilter.trim();
			todos = todos.stream()
					.filter(t -> t.getTags() != null && t.getTags().stream().anyMatch(tag -> tf.equals(tag.getName())))
					.collect(Collectors.toList());
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

		// build subtask counts: total and completed per todo
		Map<Long, Integer> subtaskTotalMap = new HashMap<>();
		Map<Long, Integer> subtaskCompletedMap = new HashMap<>();
		for (Todo t : todos) {
			int total = (t.getSubtasks() != null) ? t.getSubtasks().size() : 0;
			int completed = 0;
			if (t.getSubtasks() != null) {
				for (net.guides.springboot.todomanagement.model.Subtask st : t.getSubtasks()) {
					if (st.getStatus() == Status.COMPLETED) completed++;
				}
			}
			subtaskTotalMap.put(t.getId(), total);
			subtaskCompletedMap.put(t.getId(), completed);
		}

		model.put("todos", todos);
		model.put("subtaskTotalMap", subtaskTotalMap);
		model.put("subtaskCompletedMap", subtaskCompletedMap);
		model.put("search", search);
		model.put("statusFilter", statusFilter);
		model.put("priorityFilter", priorityFilter);
		model.put("statuses", Status.values());
		model.put("priorities", Priority.values());
		model.put("allTags", tagService.findAll());
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
		model.put("tagNames", "");
		model.put("allTags", tagService.findAll());
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
		// prepare comma-separated tag names
		if (todo.getTags() != null && !todo.getTags().isEmpty()) {
			String tagNames = todo.getTags().stream().map(t -> t.getName()).collect(Collectors.joining(", "));
			model.put("tagNames", tagNames);
		} else {
			model.put("tagNames", "");
		}
		model.put("allTags", tagService.findAll());
		return "todo";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result,
							 @RequestParam(value = "tagNames", required = false) String tagNames) {

		if (result.hasErrors()) {
			return "todo";
		}
		User user = getLoggedInUser(model);
		if (user != null) todo.setUser(user);

		// tags handled via request param 'tagNames' (comma separated)
		if (tagNames != null) {
			String[] parts = tagNames.split(",");
			java.util.Set<net.guides.springboot.todomanagement.model.Tag> tags = new java.util.HashSet<>();
			for (String p : parts) {
				String n = p.trim();
				if (n.isEmpty()) continue;
				net.guides.springboot.todomanagement.model.Tag t = tagService.createIfNotExists(n);
				if (t != null) tags.add(t);
			}
			todo.setTags(tags);
		}

		todoService.updateTodo(todo);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result,
						  @RequestParam(value = "tagNames", required = false) String tagNames) {

		if (result.hasErrors()) {
			return "todo";
		}

		User user = getLoggedInUser(model);
		if (user != null) todo.setUser(user);

		if (tagNames != null) {
			String[] parts = tagNames.split(",");
			java.util.Set<net.guides.springboot.todomanagement.model.Tag> tags = new java.util.HashSet<>();
			for (String p : parts) {
				String n = p.trim();
				if (n.isEmpty()) continue;
				net.guides.springboot.todomanagement.model.Tag t = tagService.createIfNotExists(n);
				if (t != null) tags.add(t);
			}
			todo.setTags(tags);
		}

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
