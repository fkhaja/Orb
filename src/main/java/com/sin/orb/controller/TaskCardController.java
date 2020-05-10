package com.sin.orb.controller;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.dto.TaskCardDto;
import com.sin.orb.mapper.TaskCardMapper;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.service.TaskCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("taskcards")
public class TaskCardController {
    private TaskCardService taskCardService;

    @Autowired
    public TaskCardController(TaskCardService taskCardService) {
        this.taskCardService = taskCardService;
    }

    @GetMapping
    public List<TaskCardDto> getAllTaskCards(@CurrentUser User user) {
        return user.getTaskCards()
                   .stream()
                   .map(TaskCardMapper.INSTANCE::toDto)
                   .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public TaskCardDto getTaskCard(@CurrentUser User user, @PathVariable Long id) {
        return TaskCardMapper.INSTANCE.toDto(user.getTaskCard(id));
    }

    @PostMapping
    public TaskCardDto createTaskCard(@CurrentUser User user, @RequestBody TaskCardDto taskCardDto) {
        TaskCard taskCard = TaskCardMapper.INSTANCE.toEntity(taskCardDto);
        return TaskCardMapper.INSTANCE.toDto(taskCardService.saveTaskCard(taskCard, user));
    }

    @PutMapping("{id}")
    public TaskCardDto updateTaskCard(@CurrentUser User user, @PathVariable("id") Long id, @RequestBody TaskCardDto replacementDto) {
        TaskCard existing = user.getTaskCard(id);
        TaskCard replacement = TaskCardMapper.INSTANCE.toEntity(replacementDto);
        return TaskCardMapper.INSTANCE.toDto(taskCardService.updateTaskCard(existing, replacement));
    }

    @DeleteMapping("{id}")
    public void deleteTaskCard(@CurrentUser User user, @PathVariable("id") Long id) {
        taskCardService.deleteTaskCard(user.getTaskCard(id));
    }
}
