package net.guides.springboot.todomanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guides.springboot.todomanagement.model.Tag;
import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.repository.TagRepository;
import net.guides.springboot.todomanagement.repository.TodoRepository;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TodoRepository todoRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    public Tag createIfNotExists(String name) {
        if (name == null) return null;
        String n = name.trim();
        if (n.isEmpty()) return null;
        Optional<Tag> t = tagRepository.findByName(n);
        if (t.isPresent()) return t.get();
        Tag tag = new Tag(n);
        return tagRepository.save(tag);
    }

    public List<String> findAllNames() {
        return tagRepository.findAll().stream().map(Tag::getName).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        Optional<Tag> tagOpt = tagRepository.findById(id);
        if (tagOpt.isPresent()) {
            Tag tag = tagOpt.get();
            // Remove this tag from all todos (match by id to avoid equals/hashCode issues)
            List<Todo> allTodos = todoRepository.findAll();
            for (Todo todo : allTodos) {
                if (todo.getTags() != null) {
                    boolean changed = todo.getTags().removeIf(t -> t.getId() != null && t.getId().equals(tag.getId()));
                    if (changed) {
                        todoRepository.save(todo);
                    }
                }
            }
            // Now delete the tag
            tagRepository.deleteById(id);
        }
    }
}

