package com.sin.orb.controller;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.dto.TaskDto;
import com.sin.orb.mapper.TaskMapper;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.service.TaskCardService;
import com.sin.orb.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("taskcards/{cardId}/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskCardService taskCardService;

    @Autowired
    public TaskController(TaskService taskService, TaskCardService taskCardService) {
        this.taskService = taskService;
        this.taskCardService = taskCardService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(@CurrentUser User user, @PathVariable("cardId") Long cardId) {
        List<TaskDto> taskDtos = taskService.findAllTasks(cardId, user)
                                            .stream()
                                            .map(TaskMapper.INSTANCE::toDto)
                                            .collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @PathVariable Long id) {
        Task task = taskService.findTaskById(id, cardId, user);
        return ResponseEntity.ok(TaskMapper.INSTANCE.toDto(task));
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @RequestBody TaskDto taskDTO) {
        Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
        TaskCard taskCard = taskCardService.findTaskCardForUser(cardId, user);
        Task created = taskService.saveTask(task, taskCard);
        return new ResponseEntity<>(TaskMapper.INSTANCE.toDto(created), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskDto> updateTask(@CurrentUser User user, @PathVariable("cardId") Long cardId,
                                              @PathVariable("id") Long id, @RequestBody TaskDto replacementDto) {
        Task existing = taskService.findTaskById(id, cardId, user);
        Task replacement = TaskMapper.INSTANCE.toEntity(replacementDto);
        return ResponseEntity.ok(TaskMapper.INSTANCE.toDto(taskService.updateTask(existing, replacement)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @PathVariable("id") Long id) {
        taskService.deleteTask(taskService.findTaskById(id, cardId, user));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
