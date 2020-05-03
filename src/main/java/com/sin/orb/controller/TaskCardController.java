package com.sin.orb.controller;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.exceptions.ResourceNotFoundException;
import com.sin.orb.service.TaskCardService;
import com.sin.orb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users/{userId}/taskcards")
public class TaskCardController {
    private TaskCardService taskCardService;
    private UserService userService;

    @Autowired
    public TaskCardController(TaskCardService taskCardService, UserService userService) {
        this.taskCardService = taskCardService;
        this.userService = userService;
    }


    @GetMapping
    public List<TaskCard> getAllTaskCards(@PathVariable("userId") Long userId) {
        return userService.findUserById(userId).getTaskCards();
    }

    @GetMapping("{id}")
    public TaskCard getTaskCard(@PathVariable("userId") Long userId, @PathVariable Long id) {
        return userService.findUserById(userId).getTaskCards()
                          .stream()
                          .filter(card -> card.getId().equals(id))
                          .findFirst()
                          .orElseThrow(() -> new ResourceNotFoundException("TaskCard", "id", id));
    }

    @PostMapping
    public TaskCard createTaskCard(@PathVariable("userId") Long userId, @RequestBody TaskCard taskCard) {


        taskCard.setUser(userService.findUserById(userId));
        taskCard.setCreationDate(LocalDate.now());
        taskCard.setTasks(new ArrayList<>());

        return taskCardService.saveTaskCard(taskCard);
    }

    @PutMapping("{id}")
    public TaskCard updateTaskCard(@PathVariable("userId") Long userId, @PathVariable("id") Long id, @RequestBody TaskCard replacement) {
        User user = userService.findUserById(userId);
        TaskCard existing = taskCardService.findTaskCardById(id);

        if (!user.getTaskCards().contains(existing)) throw new ResourceNotFoundException("TaskCard", "id", id);

        return taskCardService.updateTaskCard(existing, replacement);
    }

    @DeleteMapping("{id}")
    public void deleteTaskCard(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
        taskCardService.deleteTaskCard(userService.findUserById(userId).getTaskCards()
                                                  .stream()
                                                  .filter(card -> card.getId().equals(id))
                                                  .findFirst()
                                                  .orElseThrow(() -> new ResourceNotFoundException("TaskCard", "id", id)));
    }
}
