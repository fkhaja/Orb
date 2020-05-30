package com.sin.orb.service;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.repository.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task saveTask(Task task, TaskCard card) {
        task.setTaskCard(card);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task existing, Task task) {
        BeanUtils.copyProperties(task, existing, "id", "taskCard");
        return taskRepository.save(existing);
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public Page<Task> findAllTasks(Long cardId, User user, Pageable pageable) {
        return taskRepository.findAllByTaskCardIdAndTaskCardUserIs(cardId, user, pageable);
    }

    @Override
    public Task findTaskById(Long id, Long cardId, User user) {
        return taskRepository.findByIdAndTaskCardIdAndTaskCardUserIs(id, cardId, user)
                             .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    }
}
