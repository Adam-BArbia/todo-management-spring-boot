package net.guides.springboot.todomanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.guides.springboot.todomanagement.service.TagService;

@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/profile/tags")
    public String listTags(ModelMap model) {
        model.put("tags", tagService.findAll());
        return "profile-tags"; // new JSP to show tags management
    }

    @PostMapping("/tags/create")
    public String createTag(@RequestParam String name) {
        tagService.createIfNotExists(name);
        return "redirect:/profile";
    }

    @GetMapping("/tags/delete")
    public String deleteTag(@RequestParam Long id) {
        tagService.deleteById(id);
        return "redirect:/profile";
    }
}

