package com.sin.orb.controller;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.dto.TaskDto;
import com.sin.orb.mapper.TaskMapper;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("taskcards/{cardId}/tasks")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDto> getAllTasks(@CurrentUser User user, @PathVariable("cardId") Long cardId) {
        return user.getTaskCard(cardId)
                   .getTasks()
                   .stream()
                   .map(TaskMapper.INSTANCE::toDto)
                   .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public TaskDto getTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @PathVariable Long id) {
        Task task = user.getTaskCard(cardId).getTask(id);
        return TaskMapper.INSTANCE.toDto(task);
    }

    @PostMapping
    public TaskDto createTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @RequestBody TaskDto taskDTO) {
        Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
        TaskCard taskCard = user.getTaskCard(cardId);
        return TaskMapper.INSTANCE.toDto(taskService.saveTask(task, taskCard));
    }

    @PutMapping("{id}")
    public TaskDto updateTask(@CurrentUser User user, @PathVariable("cardId") Long cardId,
                              @PathVariable("id") Long id, @RequestBody TaskDto replacementDto) {
        Task existing = user.getTaskCard(cardId).getTask(id);
        Task replacement = TaskMapper.INSTANCE.toEntity(replacementDto);
        return TaskMapper.INSTANCE.toDto(taskService.updateTask(existing, replacement));
    }

    @DeleteMapping("{id}")
    public void deleteTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @PathVariable("id") Long id) {
        taskService.deleteTask(user.getTaskCard(cardId).getTask(id));
    }
}
