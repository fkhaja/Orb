package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;

public interface TaskCardService {

    TaskCard saveTaskCard(TaskCard taskCard);

    TaskCard updateTaskCard(TaskCard existing, TaskCard replacement);

    void deleteTaskCard(TaskCard taskCard);
}
