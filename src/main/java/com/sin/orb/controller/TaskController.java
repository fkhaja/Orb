package com.sin.orb.controller;

import com.sin.orb.domain.Task;
import com.sin.orb.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.findAllTasks();
    }

    @GetMapping("{id}")
    public Task getTask(@PathVariable String id) {
        return taskService.findTaskById(Long.valueOf(id));
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @PutMapping("{id}")
    public Task updateTask(@PathVariable("id") Task existingTask, @RequestBody Task task) {
        return taskService.updateTask(existingTask, task);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable("id") Task task) {
        taskService.deleteTask(task);
    }
}
