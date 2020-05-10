package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;

public interface TaskCardService {

    TaskCard saveTaskCard(TaskCard card, User user);

    TaskCard updateTaskCard(TaskCard existing, TaskCard replacement);

    void deleteTaskCard(TaskCard card);
}
