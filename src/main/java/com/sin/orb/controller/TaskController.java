package com.sin.orb.controller;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.dto.TaskDTO;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("taskcards/{cardId}/tasks")
public class TaskController {
    private TaskService taskService;
    private ModelMapper modelMapper;

    @Autowired
    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks(@CurrentUser User user, @PathVariable("cardId") Long cardId) {
        return user.getTaskCard(cardId)
                   .getTasks()
                   .stream()
                   .map(this::toDto)
                   .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public TaskDTO getTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @PathVariable Long id) {
        Task task = user.getTaskCard(cardId).getTask(id);
        return toDto(task);
    }

    @PostMapping
    public TaskDTO createTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @RequestBody TaskDTO taskDTO) {
        Task task = toEntity(taskDTO);
        TaskCard taskCard = user.getTaskCard(cardId);
        return toDto(taskService.saveTask(task, taskCard));
    }

    @PutMapping("{id}")
    public TaskDTO updateTask(@CurrentUser User user, @PathVariable("cardId") Long cardId,
                              @PathVariable("id") Long id, @RequestBody TaskDTO replacementDto) {
        Task existing = user.getTaskCard(cardId).getTask(id);
        Task replacement = toEntity(replacementDto);
        return toDto(taskService.updateTask(existing, replacement));
    }

    @DeleteMapping("{id}")
    public void deleteTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @PathVariable("id") Long id) {
        taskService.deleteTask(user.getTaskCard(cardId).getTask(id));
    }

    private TaskDTO toDto(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    private Task toEntity(TaskDTO taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }
}
