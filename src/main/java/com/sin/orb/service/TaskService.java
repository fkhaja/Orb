package com.sin.orb.service;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;

public interface TaskService {

    Task saveTask(Task task, TaskCard card);

    Task updateTask(Task existing, Task task);

    void deleteTask(Task task);
}
