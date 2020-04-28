package com.sin.orb.controller;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.exceptions.NotFoundException;
import com.sin.orb.service.TaskCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/{userId}/taskcards")
public class TaskCardController {
    private TaskCardService taskCardService;

    @Autowired
    public TaskCardController(TaskCardService taskCardService) {
        this.taskCardService = taskCardService;
    }

    @GetMapping
    public List<TaskCard> getAllTaskCards(@PathVariable("userId") User user) {
        return user.getTaskCards();
    }

    @GetMapping("{id}")
    public TaskCard getTaskCard(@PathVariable("userId") User user, @PathVariable Long id) {
        return user.getTaskCards()
                   .stream()
                   .filter(card -> card.getId().equals(id))
                   .findFirst()
                   .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public TaskCard createTaskCard(@PathVariable("userId") User user, @RequestBody TaskCard taskCard) {
        taskCard.setUser(user);
        return taskCardService.saveTaskCard(taskCard);
    }

    @PutMapping("{id}")
    public TaskCard updateTaskCard(@PathVariable("userId") User user, @PathVariable("id") TaskCard existing, @RequestBody TaskCard replacement) {
        if (!user.getTaskCards().contains(existing)) throw new NotFoundException();

        return taskCardService.updateTaskCard(existing, replacement);
    }

    @DeleteMapping("{id}")
    public void deleteTaskCard(@PathVariable("userId") User user, @PathVariable("id") Long id) {
        taskCardService.deleteTaskCard(user.getTaskCards()
                                           .stream()
                                           .filter(card -> card.getId().equals(id))
                                           .findFirst()
                                           .orElseThrow(NotFoundException::new));
    }
}
