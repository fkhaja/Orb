package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface TaskCardService {

    TaskCard saveTaskCard(@NonNull TaskCard card, User user);

    TaskCard updateTaskCard(@NonNull TaskCard existing, @NonNull TaskCard replacement);

    void deleteTaskCard(TaskCard card);

    Page<TaskCard> findAllForUser(User user, Pageable pageable);

    TaskCard findTaskCardForUser(Long id, User user);

    TaskCard partlyUpdateTaskCard(@NonNull TaskCard card, @NonNull Map<String, Object> updates);
}