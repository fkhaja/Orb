package com.sin.orb.service;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Task saveTask(Task task, TaskCard card);

    Task updateTask(Task existing, Task task);

    void deleteTask(Task task);

    Page<Task> findAllTasks(Long cardId, User user, Pageable pageable);

    Task findTaskById(Long id, Long cardId, User user);
}
