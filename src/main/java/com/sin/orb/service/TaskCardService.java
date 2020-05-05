package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;

import java.util.List;

public interface TaskCardService {

    TaskCard saveTaskCard(TaskCard taskCard);

    TaskCard updateTaskCard(TaskCard existing, TaskCard replacement);

    void deleteTaskCard(TaskCard taskCard);

    List<TaskCard> findAllTaskCards(Long userId);

    TaskCard findTaskCardById(Long id, Long userId);
}
