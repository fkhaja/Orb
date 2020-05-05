package com.sin.orb.controller;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.dto.TaskCardDTO;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.security.UserPrincipal;
import com.sin.orb.service.TaskCardService;
import com.sin.orb.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("taskcards")
public class TaskCardController {
    private TaskCardService taskCardService;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public TaskCardController(TaskCardService taskCardService, UserService userService, ModelMapper modelMapper) {
        this.taskCardService = taskCardService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<TaskCardDTO> getAllTaskCards(@CurrentUser UserPrincipal userPrincipal) {
        return taskCardService.findAllTaskCards(userPrincipal.getId())
                              .stream()
                              .map(this::toDto)
                              .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public TaskCardDTO getTaskCard(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id) {
        return toDto(taskCardService.findTaskCardById(id, userPrincipal.getId()));
    }

    @PostMapping
    public TaskCardDTO createTaskCard(@CurrentUser UserPrincipal userPrincipal, @RequestBody TaskCardDTO taskCardDto) {
        TaskCard taskCard = toEntity(taskCardDto);
        taskCard.setUser(userService.findUserById(userPrincipal.getId()));
        taskCard.setCreationDate(LocalDate.now());
        taskCard.setTasks(new ArrayList<>());

        return toDto(taskCardService.saveTaskCard(taskCard));
    }

    @PutMapping("{id}")
    public TaskCardDTO updateTaskCard(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") Long id, @RequestBody TaskCardDTO replacementDto) {
        TaskCard existing = taskCardService.findTaskCardById(id, userPrincipal.getId());
        TaskCard replacement = toEntity(replacementDto);
        return toDto(taskCardService.updateTaskCard(existing, replacement));
    }

    @DeleteMapping("{id}")
    public void deleteTaskCard(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") Long id) {
        taskCardService.deleteTaskCard(taskCardService.findTaskCardById(id, userPrincipal.getId()));
    }

    private TaskCardDTO toDto(TaskCard taskCard) {
        return modelMapper.map(taskCard, TaskCardDTO.class);
    }

    private TaskCard toEntity(TaskCardDTO taskCardDto) {
        return modelMapper.map(taskCardDto, TaskCard.class);
    }
}
