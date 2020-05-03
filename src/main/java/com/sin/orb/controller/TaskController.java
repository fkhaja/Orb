package com.sin.orb.controller;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.exceptions.ResourceNotFoundException;
import com.sin.orb.service.TaskService;
import com.sin.orb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/{userId}/taskcards/{cardId}/tasks")
public class TaskController {
    private final TaskService taskService;
    private UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public List<Task> getAllTasks(@PathVariable("userId") Long userId, @PathVariable("cardId") Long cardId) {
        return findTaskCard(userService.findUserById(userId).getTaskCards(), cardId).getTasks();
    }

    @GetMapping("{id}")
    public Task getTask(@PathVariable("userId") Long userId, @PathVariable("cardId") Long cardId, @PathVariable Long id) {
        return findTaskCard(userService.findUserById(userId).getTaskCards(), cardId)
                .getTasks()
                .stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    }

    @PostMapping
    public Task createTask(@PathVariable("userId") Long userId, @PathVariable("cardId") Long cardId, @RequestBody Task task) {
        task.setTaskCard(findTaskCard(userService.findUserById(userId).getTaskCards(), cardId));
        return taskService.saveTask(task);
    }

    @PutMapping("{id}")
    public Task updateTask(@PathVariable("userId") Long userId, @PathVariable("cardId") Long cardId,
                           @PathVariable("id") Long id, @RequestBody Task replacement) {
        Task existing = findTaskCard(userService.findUserById(userId).getTaskCards(), cardId)
                .getTasks()
                .stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        return taskService.updateTask(existing, replacement);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable("userId") Long userId, @PathVariable("cardId") Long cardId, @PathVariable("id") Long id) {
        taskService.deleteTask(findTaskCard(userService.findUserById(userId).getTaskCards(), cardId)
                                       .getTasks()
                                       .stream()
                                       .filter(task -> task.getId().equals(id))
                                       .findFirst()
                                       .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id)));
    }

    private TaskCard findTaskCard(List<TaskCard> cards, Long id) {
        return cards.stream()
                    .filter(card -> card.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("TaskCard", "id", id));
    }
}
