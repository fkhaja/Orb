package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;

import java.util.List;

public interface TaskCardService {

    TaskCard saveTaskCard(TaskCard taskCard);

    List<TaskCard> findAllTaskCards();

    TaskCard findTaskCardById(Long id);

    TaskCard updateTaskCard(TaskCard existing, TaskCard replacement);

    void deleteTaskCard(TaskCard taskCard);
}
