package com.sin.orb.controller;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.service.TaskCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taskcards")
public class TaskCardController {
    private TaskCardService taskCardService;

    @Autowired
    public TaskCardController(TaskCardService taskCardService) {
        this.taskCardService = taskCardService;
    }

    @GetMapping
    public List<TaskCard> getAllTaskCards() {
        return taskCardService.findAllTaskCards();
    }

    @GetMapping("{id}")
    public TaskCard getTaskCard(@PathVariable String id) {
        return taskCardService.findTaskCardById(Long.valueOf(id));
    }

    @PostMapping
    public TaskCard createTaskCard(@RequestBody TaskCard taskCard) {
        return taskCardService.saveTaskCard(taskCard);
    }

    @PutMapping("{id}")
    public TaskCard updateTaskCard(@PathVariable("id") TaskCard existing, @RequestBody TaskCard replacement) {
        return taskCardService.updateTaskCard(existing, replacement);
    }

    @DeleteMapping("{id}")
    public void deleteTaskCard(@PathVariable("id") TaskCard taskCard){
        taskCardService.deleteTaskCard(taskCard);
    }
}
