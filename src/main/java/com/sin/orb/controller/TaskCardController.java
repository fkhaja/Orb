package com.sin.orb.controller;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.dto.TaskCardDto;
import com.sin.orb.mapper.TaskCardMapper;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.service.TaskCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

@Api
@RestController
@RequestMapping("taskcards")
public class TaskCardController {
    private final TaskCardService taskCardService;

    @Autowired
    public TaskCardController(TaskCardService taskCardService) {
        this.taskCardService = taskCardService;
    }

    @GetMapping
    @ApiOperation("Get all task cards")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "integer", value = "page number"),
            @ApiImplicitParam(name = "size", paramType = "integer", value = "page size"),
            @ApiImplicitParam(name = "sort", paramType = "string", value = "direction and field sort")
    })
    public ResponseEntity<Page<TaskCardDto>> getAllTaskCards(@ApiIgnore @CurrentUser User user,
                                                             @ApiIgnore @PageableDefault(sort = "id") Pageable pageable) {
        Page<TaskCard> cards = taskCardService.findAllForUser(user, pageable);
        return ResponseEntity.ok(cards.map(TaskCardMapper.INSTANCE::toDto));
    }

    @GetMapping("{id}")
    @ApiOperation("Get a task card with the specified id")
    public ResponseEntity<TaskCardDto> getTaskCard(@ApiIgnore @CurrentUser User user, @PathVariable Long id) {
        return ResponseEntity.ok(TaskCardMapper.INSTANCE.toDto(taskCardService.findTaskCardForUser(id, user)));
    }

    @PostMapping
    @ApiOperation("Create a task card")
    public ResponseEntity<TaskCardDto> createTaskCard(@ApiIgnore @CurrentUser User user, @Valid @RequestBody TaskCardDto taskCardDto) {
        TaskCard taskCard = TaskCardMapper.INSTANCE.toEntity(taskCardDto);
        TaskCard created = taskCardService.saveTaskCard(taskCard, user);
        return new ResponseEntity<>(TaskCardMapper.INSTANCE.toDto(created), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ApiOperation("Update the task card with the specified id")
    public ResponseEntity<TaskCardDto> updateTaskCard(@ApiIgnore @CurrentUser User user, @PathVariable("id") Long id,
                                                      @Valid @RequestBody TaskCardDto replacementDto) {
        TaskCard existing = taskCardService.findTaskCardForUser(id, user);
        TaskCard replacement = TaskCardMapper.INSTANCE.toEntity(replacementDto);
        return ResponseEntity.ok(TaskCardMapper.INSTANCE.toDto(taskCardService.updateTaskCard(existing, replacement)));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete a task card with the specified id")
    public ResponseEntity<Void> deleteTaskCard(@ApiIgnore @CurrentUser User user, @PathVariable("id") Long id) {
        taskCardService.deleteTaskCard(taskCardService.findTaskCardForUser(id, user));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{id}")
    @ApiOperation("Patch an existing task card with partial update")
    public ResponseEntity<TaskCardDto> patchTaskCard(@ApiIgnore @CurrentUser User user, @PathVariable("id") Long id,
                                                     @RequestBody Map<String, Object> updates) {
        TaskCard taskCard = taskCardService.findTaskCardForUser(id, user);
        TaskCard updated = taskCardService.partlyUpdateTaskCard(taskCard, updates);
        return ResponseEntity.ok(TaskCardMapper.INSTANCE.toDto(updated));
    }
}
