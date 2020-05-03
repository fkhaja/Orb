package com.sin.orb.controller;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.exceptions.ResourceNotFoundException;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.security.UserPrincipal;
import com.sin.orb.service.TaskService;
import com.sin.orb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("taskcards/{cardId}/tasks")
public class TaskController {
    private final TaskService taskService;
    private UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public List<Task> getAllTasks(@CurrentUser UserPrincipal userPrincipal, @PathVariable("cardId") Long cardId) {
        return findTaskCard(userService.findUserById(userPrincipal.getId()).getTaskCards(), cardId).getTasks();
    }

    @GetMapping("{id}")
    public Task getTask(@CurrentUser UserPrincipal userPrincipal, @PathVariable("cardId") Long cardId, @PathVariable Long id) {
        return findTask(findTaskCard(userService.findUserById(userPrincipal.getId()).getTaskCards(), cardId).getTasks(), id);
    }

    @PostMapping
    public Task createTask(@CurrentUser UserPrincipal userPrincipal, @PathVariable("cardId") Long cardId, @RequestBody Task task) {
        task.setTaskCard(findTaskCard(userService.findUserById(userPrincipal.getId()).getTaskCards(), cardId));
        return taskService.saveTask(task);
    }

    @PutMapping("{id}")
    public Task updateTask(@CurrentUser UserPrincipal userPrincipal, @PathVariable("cardId") Long cardId,
                           @PathVariable("id") Long id, @RequestBody Task replacement) {
        Task existing = findTask(findTaskCard(userService.findUserById(userPrincipal.getId()).getTaskCards(), cardId).getTasks(), id);
        return taskService.updateTask(existing, replacement);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@CurrentUser UserPrincipal userPrincipal, @PathVariable("cardId") Long cardId, @PathVariable("id") Long id) {
        taskService.deleteTask(findTask(findTaskCard(userService.findUserById(userPrincipal.getId()).getTaskCards(), cardId).getTasks(), id));
    }

    private TaskCard findTaskCard(List<TaskCard> cards, Long id) {
        return cards.stream()
                    .filter(card -> card.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Task card", "id", id));
    }

    private Task findTask(List<Task> tasks, Long id) {
        return tasks.stream()
                    .filter(task -> task.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    }
}
