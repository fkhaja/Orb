package com.sin.orb.service;

import com.sin.orb.domain.Task;

public interface TaskService {

    Task saveTask(Task task);

    Task updateTask(Task existingTask, Task task);

    void deleteTask(Task task);

    Task findTaskById(Long id);
}
