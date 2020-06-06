package com.sin.orb.controller;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.dto.TaskCardDto;
import com.sin.orb.mapper.TaskCardMapper;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.service.TaskCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("taskcards")
public class TaskCardController {
    private final TaskCardService taskCardService;

    @Autowired
    public TaskCardController(TaskCardService taskCardService) {
        this.taskCardService = taskCardService;
    }

    @GetMapping
    public ResponseEntity<Page<TaskCardDto>> getAllTaskCards(@CurrentUser User user, @PageableDefault(sort = "id") Pageable pageable) {
        Page<TaskCard> cards = taskCardService.findAllForUser(user, pageable);
        return ResponseEntity.ok(cards.map(TaskCardMapper.INSTANCE::toDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskCardDto> getTaskCard(@CurrentUser User user, @PathVariable Long id) {
        return ResponseEntity.ok(TaskCardMapper.INSTANCE.toDto(taskCardService.findTaskCardForUser(id, user)));
    }

    @PostMapping
    public ResponseEntity<TaskCardDto> createTaskCard(@CurrentUser User user, @Valid @RequestBody TaskCardDto taskCardDto) {
        TaskCard taskCard = TaskCardMapper.INSTANCE.toEntity(taskCardDto);
        TaskCard created = taskCardService.saveTaskCard(taskCard, user);
        return new ResponseEntity<>(TaskCardMapper.INSTANCE.toDto(created), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskCardDto> updateTaskCard(@CurrentUser User user, @PathVariable("id") Long id,
                                                      @Valid @RequestBody TaskCardDto replacementDto) {
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
