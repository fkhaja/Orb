package com.sin.orb.controller;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.dto.TaskCardDto;
import com.sin.orb.mapper.TaskCardMapper;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.service.TaskCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("taskcards")
public class TaskCardController {
    private final TaskCardService taskCardService;

    @Autowired
    public TaskCardController(TaskCardService taskCardService) {
        this.taskCardService = taskCardService;
    }

    @GetMapping
    public ResponseEntity<List<TaskCardDto>> getAllTaskCards(@CurrentUser User user) {
        return ResponseEntity.ok(taskCardService.findAllForUser(user)
                                                .stream()
                                                .map(TaskCardMapper.INSTANCE::toDto)
                                                .collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskCardDto> getTaskCard(@CurrentUser User user, @PathVariable Long id) {
        return ResponseEntity.ok(TaskCardMapper.INSTANCE.toDto(taskCardService.findTaskCardForUser(id, user)));
    }

    @PostMapping
    public ResponseEntity<TaskCardDto> createTaskCard(@CurrentUser User user, @RequestBody TaskCardDto taskCardDto) {
        TaskCard taskCard = TaskCardMapper.INSTANCE.toEntity(taskCardDto);
        TaskCard created = taskCardService.saveTaskCard(taskCard, user);
        return new ResponseEntity<>(TaskCardMapper.INSTANCE.toDto(created), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskCardDto> updateTaskCard(@CurrentUser User user, @PathVariable("id") Long id, @RequestBody TaskCardDto replacementDto) {
        TaskCard existing = taskCardService.findTaskCardForUser(id, user);
        TaskCard replacement = TaskCardMapper.INSTANCE.toEntity(replacementDto);
        return ResponseEntity.ok(TaskCardMapper.INSTANCE.toDto(taskCardService.updateTaskCard(existing, replacement)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTaskCard(@CurrentUser User user, @PathVariable("id") Long id) {
        taskCardService.deleteTaskCard(taskCardService.findTaskCardForUser(id, user));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
