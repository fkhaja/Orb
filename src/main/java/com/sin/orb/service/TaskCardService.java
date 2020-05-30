package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskCardService {

    TaskCard saveTaskCard(TaskCard card, User user);

    TaskCard updateTaskCard(TaskCard existing, TaskCard replacement);

    void deleteTaskCard(TaskCard card);

    Page<TaskCard> findAllForUser(User user, Pageable pageable);

    TaskCard findTaskCardForUser(Long id, User user);
}
