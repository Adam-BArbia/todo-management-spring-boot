package net.guides.springboot.todomanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guides.springboot.todomanagement.model.Subtask;
import net.guides.springboot.todomanagement.model.Status;
import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.repository.SubtaskRepository;
import net.guides.springboot.todomanagement.repository.TodoRepository;

@Service
public class SubtaskService {

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Autowired
    private TodoRepository todoRepository;

    public List<Subtask> findByTodoId(Long todoId) {
        return subtaskRepository.findByTodo_Id(todoId);
    }

    public Optional<Subtask> findById(Long id) {
        return subtaskRepository.findById(id);
    }

    public Subtask save(Subtask s) {
        return subtaskRepository.save(s);
    }

    public void deleteById(Long id) {
        subtaskRepository.deleteById(id);
    }

    /** Update subtask status and auto-update parent todo status per rules:
     * - If at least one subtask COMPLETED => parent IN_PROGRESS
     * - If all subtasks COMPLETED => parent COMPLETED
     */
    public void updateStatus(Long subtaskId, Status newStatus) {
        Optional<Subtask> opt = subtaskRepository.findById(subtaskId);
        if (!opt.isPresent()) return;
        Subtask s = opt.get();
        s.setStatus(newStatus);
        subtaskRepository.save(s);

        // update parent todo status according to subtasks
        Todo todo = s.getTodo();
        if (todo == null) return;
        List<Subtask> subs = subtaskRepository.findByTodo_Id(todo.getId());
        long total = subs.size();
        long completed = subs.stream().filter(sb -> sb.getStatus() == Status.COMPLETED).count();
        if (total == 0) return;
        if (completed == total) {
            todo.setStatus(Status.COMPLETED);
        } else if (completed > 0) {
            todo.setStatus(Status.IN_PROGRESS);
        } else {
            // no subtasks completed -> do not force TODO if user had set another status? requirement: mark in progress when at least one done and complete when all done
            // we'll set to TODO if none done
            todo.setStatus(Status.TODO);
        }
        todoRepository.save(todo);
    }
}

