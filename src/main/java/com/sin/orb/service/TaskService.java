package com.sin.orb.service;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;

import java.util.List;

public interface TaskService {

    Task saveTask(Task task, TaskCard card);

    Task updateTask(Task existing, Task task);

    void deleteTask(Task task);

    List<Task> findAllTasks(Long cardId, User user);

    Task findTaskById(Long id, Long cardId, User user);
}
