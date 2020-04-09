package com.sin.orb.service;

import com.sin.orb.domain.Task;

import java.util.List;

public interface TaskService {

    Task saveTask(Task task);

    List<Task> findAllTasks();

    Task findTaskById(Long id);

    Task updateTask(Task existingTask, Task task);

    void deleteTask(Task task);
}
