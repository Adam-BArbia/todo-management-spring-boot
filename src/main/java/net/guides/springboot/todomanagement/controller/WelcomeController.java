package net.guides.springboot.todomanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.guides.springboot.todomanagement.service.ITodoService;
import net.guides.springboot.todomanagement.model.User;
import net.guides.springboot.todomanagement.service.UserService;

@Controller
public class WelcomeController {

	@Autowired
	private ITodoService todoService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showWelcomePage(ModelMap model) {
		String name = getLoggedinUserName();
		model.put("name", name);
		User user = userService.findByUsername(name);
		model.put("todoCount", (user != null) ? todoService.getTodosByUserId(user.getId()).size() : 0);
		return "welcome";
	}

	private String getLoggedinUserName() {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return ""; // no authenticated principal
		}
		Object principal = auth.getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return (principal != null) ? principal.toString() : "";
	}
}
