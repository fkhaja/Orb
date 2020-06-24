package com.sin.orb.controller;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.dto.TaskDto;
import com.sin.orb.mapper.TaskMapper;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.service.TaskCardService;
import com.sin.orb.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api
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
    @ApiOperation("Get all tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(@ApiIgnore @CurrentUser User user, @PathVariable("cardId") Long cardId) {
        List<TaskDto> taskDtos = taskService.findAllTasks(cardId, user)
                                            .stream()
                                            .map(TaskMapper.INSTANCE::toDto)
                                            .collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
    }

    @GetMapping("{id}")
    @ApiOperation("Get a task with the specified id")
    public ResponseEntity<TaskDto> getTask(@ApiIgnore @CurrentUser User user, @PathVariable("cardId") Long cardId,
                                           @PathVariable Long id) {
        Task task = taskService.findTaskById(id, cardId, user);
        return ResponseEntity.ok(TaskMapper.INSTANCE.toDto(task));
    }

    @PostMapping
    @ApiOperation("Create a task")
    public ResponseEntity<TaskDto> createTask(@ApiIgnore @CurrentUser User user, @PathVariable("cardId") Long cardId,
                                              @Valid @RequestBody TaskDto taskDTO) {
        Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
        TaskCard taskCard = taskCardService.findTaskCardForUser(cardId, user);
        Task created = taskService.saveTask(task, taskCard);
        return new ResponseEntity<>(TaskMapper.INSTANCE.toDto(created), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ApiOperation("Update an existing task with the specified id")
    public ResponseEntity<TaskDto> updateTask(@ApiIgnore @CurrentUser User user, @PathVariable("cardId") Long cardId,
                                              @PathVariable("id") Long id, @Valid @RequestBody TaskDto replacementDto) {
        Task existing = taskService.findTaskById(id, cardId, user);
        Task replacement = TaskMapper.INSTANCE.toEntity(replacementDto);
        return ResponseEntity.ok(TaskMapper.INSTANCE.toDto(taskService.updateTask(existing, replacement)));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete a task with the specified id")
    public ResponseEntity<Void> deleteTask(@ApiIgnore @CurrentUser User user, @PathVariable("cardId") Long cardId,
                                           @PathVariable("id") Long id) {
        taskService.deleteTask(taskService.findTaskById(id, cardId, user));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{id}")
    @ApiOperation("Patch an existing task with partial update")
    public ResponseEntity<TaskDto> patchTask(@ApiIgnore @CurrentUser User user, @PathVariable("id") Long id,
                                             @PathVariable("cardId") Long cardId, @RequestBody Map<String, Object> updates) {
        Task task = taskService.findTaskById(id, cardId, user);
        if (!updates.isEmpty()) {
            Task updated = taskService.partlyUpdateTask(task, updates);
            return ResponseEntity.ok(TaskMapper.INSTANCE.toDto(updated));
        }
        return ResponseEntity.ok(TaskMapper.INSTANCE.toDto(task));
    }
}