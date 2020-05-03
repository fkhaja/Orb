package com.sin.orb.controller;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.exceptions.ResourceNotFoundException;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.security.UserPrincipal;
import com.sin.orb.service.TaskCardService;
import com.sin.orb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("taskcards")
public class TaskCardController {
    private TaskCardService taskCardService;
    private UserService userService;

    @Autowired
    public TaskCardController(TaskCardService taskCardService, UserService userService) {
        this.taskCardService = taskCardService;
        this.userService = userService;
    }

    @GetMapping
    public List<TaskCard> getAllTaskCards(@CurrentUser UserPrincipal userPrincipal) {
        return userService.findUserById(userPrincipal.getId()).getTaskCards();
    }

    @GetMapping("{id}")
    public TaskCard getTaskCard(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id) {
        return findTaskCard(userService.findUserById(userPrincipal.getId()).getTaskCards(), id);
    }

    @PostMapping
    public TaskCard createTaskCard(@CurrentUser UserPrincipal userPrincipal, @RequestBody TaskCard taskCard) {
        taskCard.setUser(userService.findUserById(userPrincipal.getId()));
        taskCard.setCreationDate(LocalDate.now());
        taskCard.setTasks(new ArrayList<>());

        return taskCardService.saveTaskCard(taskCard);
    }

    @PutMapping("{id}")
    public TaskCard updateTaskCard(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") Long id, @RequestBody TaskCard replacement) {
        TaskCard existing = findTaskCard(userService.findUserById(userPrincipal.getId()).getTaskCards(), id);
        return taskCardService.updateTaskCard(existing, replacement);
    }

    @DeleteMapping("{id}")
    public void deleteTaskCard(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") Long id) {
        taskCardService.deleteTaskCard(findTaskCard(userService.findUserById(userPrincipal.getId()).getTaskCards(), id));
    }

    private TaskCard findTaskCard(List<TaskCard> cards, Long id) {
        return cards.stream()
                    .filter(card -> card.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Task card", "id", id));
    }
}
