package net.guides.springboot.todomanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guides.springboot.todomanagement.model.Tag;
import net.guides.springboot.todomanagement.repository.TagRepository;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

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
        tagRepository.deleteById(id);
    }
}

