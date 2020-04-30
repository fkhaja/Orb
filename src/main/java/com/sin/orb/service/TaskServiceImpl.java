package com.sin.orb.service;

import com.sin.orb.domain.Task;
import com.sin.orb.exceptions.NotFoundException;
import com.sin.orb.repo.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Task updateTask(Task existingTask, Task task) {
        BeanUtils.copyProperties(task, existingTask, "id", "taskCard");
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
}
