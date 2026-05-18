package net.guides.springboot.todomanagement.controller;

import net.guides.springboot.todomanagement.model.User;
import net.guides.springboot.todomanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public String listUsers(ModelMap model) {
        model.put("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/admin/edit-user")
    public String editUser(@RequestParam Long id, ModelMap model) {
        User u = userService.findById(id);
        if (u == null) {
            return "redirect:/admin/users";
        }
        model.put("user", u);
        return "admin/edit-user";
    }

    @PostMapping("/admin/update-user")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String role,
                             @RequestParam(required = false) String enabled) {
        boolean isEnabled = (enabled != null && enabled.equals("on"));
        userService.updateRoleAndEnabled(id, role, isEnabled);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/delete-user")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}

