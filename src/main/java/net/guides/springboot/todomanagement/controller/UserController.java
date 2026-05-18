package net.guides.springboot.todomanagement.controller;

import net.guides.springboot.todomanagement.model.User;
import net.guides.springboot.todomanagement.service.ITodoService;
import net.guides.springboot.todomanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ITodoService todoService;

    private String getLoggedInUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(ModelMap model) {
        String username = getLoggedInUserName();
        User user = userService.findByUsername(username);

        model.put("username", username);
        model.put("lastLogin", user != null ? user.getLastLogin() : null);
        model.put("role", user != null ? user.getRole() : "USER");
        model.put("enabled", user == null || user.isEnabled());
        User u = userService.findByUsername(username);
        model.put("todoCount", (u != null) ? todoService.getTodosByUserId(u.getId()).size() : 0);
        return "profile";
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.GET)
    public String showChangePasswordPage() {
        return "change-password";
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 ModelMap model) {
        if (currentPassword == null || currentPassword.trim().isEmpty()) {
            model.put("error", "Enter your current password");
            return "change-password";
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            model.put("error", "Enter a new password");
            return "change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.put("error", "New passwords do not match");
            return "change-password";
        }

        if (newPassword.length() < 6) {
            model.put("error", "Password must be at least 6 characters");
            return "change-password";
        }

        boolean changed = userService.changePassword(getLoggedInUserName(), currentPassword, newPassword);
        if (!changed) {
            model.put("error", "Current password is incorrect");
            return "change-password";
        }

        return "redirect:/profile?passwordChanged=true";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterPage() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           ModelMap model) {
        username = username == null ? "" : username.trim();

        if (username.isEmpty()) {
            model.put("error", "Username is required");
            return "register";
        }

        if (username.length() < 3 || username.length() > 20) {
            model.put("error", "Username must be 3 to 20 characters");
            return "register";
        }

        if (!username.matches("^[A-Za-z0-9_.-]+$")) {
            model.put("error", "Username can contain letters, numbers, underscore, dot, and hyphen only");
            return "register";
        }

        if (password == null || password.trim().isEmpty()) {
            model.put("error", "Password is required");
            return "register";
        }

        if (!password.equals(confirmPassword)) {
            model.put("error", "Passwords do not match");
            return "register";
        }

        if (password.length() < 6) {
            model.put("error", "Password must be at least 6 characters");
            return "register";
        }

        if (userService.findByUsername(username) != null) {
            model.put("error", "Username already exists");
            return "register";
        }

        userService.saveUser(new User(username, password, "USER"));
        return "redirect:/login?registered=true";
    }
}