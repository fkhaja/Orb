package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;

import java.util.List;

public interface TaskCardService {

    TaskCard saveTaskCard(TaskCard card, User user);

    TaskCard updateTaskCard(TaskCard existing, TaskCard replacement);

    void deleteTaskCard(TaskCard card);

    List<TaskCard> findAllForUser(User user);

    TaskCard findTaskCardForUser(Long id, User user);
}
