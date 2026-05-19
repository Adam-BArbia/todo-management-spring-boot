package net.guides.springboot.todomanagement.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.guides.springboot.todomanagement.model.Subtask;
import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.model.Status;
import net.guides.springboot.todomanagement.service.SubtaskService;
import net.guides.springboot.todomanagement.repository.TodoRepository;

@Controller
public class SubtaskController {

    @Autowired
    private SubtaskService subtaskService;

    @Autowired
    private TodoRepository todoRepository;

    @PostMapping("/subtask/create")
    public String createSubtask(@RequestParam Long todoId, @RequestParam String name, @RequestParam(required = false) String dueFrom) throws Exception {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        if (todo == null) return "redirect:/list-todos";
        Date due = null;
        if (dueFrom != null && !dueFrom.trim().isEmpty()) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            due = df.parse(dueFrom.trim());
        }
        Subtask s = new Subtask(todo, name, due);
        subtaskService.save(s);
        return "redirect:/list-todos";
    }

    @GetMapping("/subtask/update-status")
    public String updateStatus(@RequestParam Long id, @RequestParam String status) {
        try {
            Status s = Status.valueOf(status);
            subtaskService.updateStatus(id, s);
        } catch (IllegalArgumentException e) {
            // ignore
        }
        return "redirect:/list-todos";
    }
}

