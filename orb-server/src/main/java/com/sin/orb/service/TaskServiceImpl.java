package com.sin.orb.service;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.repository.TaskRepository;
import com.sin.orb.util.CustomBeanUtils;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    @Override
    @Transactional
    public Task saveTask(@NonNull Task task, TaskCard card) {
        task.setTaskCard(card);
        return repository.save(task);
    }

    @Override
    @Transactional
    public Task updateTask(@NonNull Task existing, @NonNull Task task) {
        BeanUtils.copyProperties(task, existing, "id", "taskCard");
        return existing;
    }

    @Override
    @Transactional
    public void deleteTask(Task task) {
        repository.delete(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAllTasks(Long cardId, User user) {
        return repository.findAllByTaskCardIdAndTaskCardUserIs(cardId, user);
    }

    @Override
    @Transactional(readOnly = true)
    public Task findTaskById(Long id, Long cardId, User user) {
        return repository.findByIdAndTaskCardIdAndTaskCardUserIs(id, cardId, user)
                         .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    }

    @Override
    @Transactional
    public Task partlyUpdateTask(@NonNull Task task, @NonNull Map<String, Object> updates) {
        CustomBeanUtils.populate(task, updates, Task.class, "id", "taskCard");
        return task;
    }
}