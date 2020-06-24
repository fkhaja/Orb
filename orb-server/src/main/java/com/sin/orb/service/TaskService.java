package com.sin.orb.service;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

public interface TaskService {

    Task saveTask(@NonNull Task task, TaskCard card);

    Task updateTask(@NonNull Task existing, @NonNull Task replacement);

    void deleteTask(Task task);

    List<Task> findAllTasks(Long cardId, User user);

    Task findTaskById(Long id, Long cardId, User user);

    Task partlyUpdateTask(@NonNull Task task, @NonNull Map<String, Object> updates);
}