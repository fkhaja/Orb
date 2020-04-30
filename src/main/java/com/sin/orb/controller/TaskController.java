package com.sin.orb.controller;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.exceptions.NotFoundException;
import com.sin.orb.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("taskcards/{cardId}/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks(@PathVariable("cardId") TaskCard taskCard) {
        return taskCard.getTasks();
    }

    @GetMapping("{id}")
    public Task getTask(@PathVariable("cardId") TaskCard taskCard, @PathVariable Long id) {
        return taskCard.getTasks()
                       .stream()
                       .filter(task -> task.getId().equals(id))
                       .findFirst()
                       .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Task createTask(@PathVariable("cardId") TaskCard taskCard, @RequestBody Task task) {
        task.setTaskCard(taskCard);
        return taskService.saveTask(task);
    }

    @PutMapping("{id}")
    public Task updateTask(@PathVariable("cardId") TaskCard taskCard, @PathVariable("id") Task existing, @RequestBody Task replacement) {
        if(!taskCard.getTasks().contains(existing)) throw new NotFoundException();

        return taskService.updateTask(existing, replacement);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable("cardId") TaskCard taskCard, @PathVariable("id") Long id) {
        taskService.deleteTask(taskCard.getTasks()
                                       .stream()
                                       .filter(task -> task.getId().equals(id))
                                       .findFirst()
                                       .orElseThrow(NotFoundException::new));
    }
}
