package com.sin.orb.controller;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.dto.TaskCardDTO;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.service.TaskCardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("taskcards")
public class TaskCardController {
    private TaskCardService taskCardService;
    private ModelMapper modelMapper;

    @Autowired
    public TaskCardController(TaskCardService taskCardService, ModelMapper modelMapper) {
        this.taskCardService = taskCardService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<TaskCardDTO> getAllTaskCards(@CurrentUser User user) {
        return user.getTaskCards()
                   .stream()
                   .map(this::toDto)
                   .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public TaskCardDTO getTaskCard(@CurrentUser User user, @PathVariable Long id) {
        return toDto(findTaskCard(user.getTaskCards(), id));
    }

    @PostMapping
    public TaskCardDTO createTaskCard(@CurrentUser User user, @RequestBody TaskCardDTO taskCardDto) {
        TaskCard taskCard = toEntity(taskCardDto);
        return toDto(taskCardService.saveTaskCard(taskCard, user));
    }

    @PutMapping("{id}")
    public TaskCardDTO updateTaskCard(@CurrentUser User user, @PathVariable("id") Long id, @RequestBody TaskCardDTO replacementDto) {
        TaskCard existing = findTaskCard(user.getTaskCards(), id);
        TaskCard replacement = toEntity(replacementDto);
        return toDto(taskCardService.updateTaskCard(existing, replacement));
    }

    @DeleteMapping("{id}")
    public void deleteTaskCard(@CurrentUser User user, @PathVariable("id") Long id) {
        taskCardService.deleteTaskCard(findTaskCard(user.getTaskCards(), id));
    }

    private TaskCardDTO toDto(TaskCard taskCard) {
        return modelMapper.map(taskCard, TaskCardDTO.class);
    }

    private TaskCard toEntity(TaskCardDTO taskCardDto) {
        return modelMapper.map(taskCardDto, TaskCard.class);
    }

    private TaskCard findTaskCard(List<TaskCard> cards, Long id) {
        return cards.stream()
                    .filter(card -> card.getId().equals(id))
                    .findFirst()
                    .orElse(null);
    }
}
