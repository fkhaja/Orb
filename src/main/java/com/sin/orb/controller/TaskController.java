package com.sin.orb.controller;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.dto.TaskDTO;
import com.sin.orb.exceptions.ResourceNotFoundException;
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
        return findTaskCard(user.getTaskCards(), cardId)
                .getTasks()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public TaskDTO getTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @PathVariable Long id) {
        Task task = findTask(findTaskCard(user.getTaskCards(), cardId).getTasks(), id);
        return toDto(task);
    }

    @PostMapping
    public TaskDTO createTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @RequestBody TaskDTO taskDTO) {
        Task task = toEntity(taskDTO);
        return toDto(taskService.saveTask(task, findTaskCard(user.getTaskCards(), cardId)));
    }

    @PutMapping("{id}")
    public TaskDTO updateTask(@CurrentUser User user, @PathVariable("cardId") Long cardId,
                              @PathVariable("id") Long id, @RequestBody TaskDTO replacementDto) {
        Task existing = findTask(findTaskCard(user.getTaskCards(), cardId).getTasks(), id);
        Task replacement = toEntity(replacementDto);
        return toDto(taskService.updateTask(existing, replacement));
    }

    @DeleteMapping("{id}")
    public void deleteTask(@CurrentUser User user, @PathVariable("cardId") Long cardId, @PathVariable("id") Long id) {
        taskService.deleteTask(findTask(findTaskCard(user.getTaskCards(), cardId).getTasks(), id));
    }

    private TaskCard findTaskCard(List<TaskCard> cards, Long id) {
        return cards.stream()
                    .filter(card -> card.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Task card", "id", id));
    }

    private Task findTask(List<Task> tasks, Long id) {
        return tasks.stream()
                    .filter(task -> task.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    }

    private TaskDTO toDto(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    private Task toEntity(TaskDTO taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }
}
