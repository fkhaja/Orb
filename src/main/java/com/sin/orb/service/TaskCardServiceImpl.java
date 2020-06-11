package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.repository.TaskCardRepository;
import com.sin.orb.util.CustomBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class TaskCardServiceImpl implements TaskCardService {
    private final TaskCardRepository repository;

    @Autowired
    public TaskCardServiceImpl(TaskCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskCard saveTaskCard(TaskCard taskCard, User user) {
        taskCard.setUser(user);
        taskCard.setCreationDate(LocalDate.now());
        taskCard.setTasks(new ArrayList<>());

        return repository.save(taskCard);
    }

    @Override
    public TaskCard updateTaskCard(TaskCard existing, TaskCard replacement) {
        CustomBeanUtils.copyPropsIgnoringNulls(replacement, existing, "id", "user", "creationDate", "tasks");
        return repository.save(existing);
    }

    @Override
    public void deleteTaskCard(TaskCard taskCard) {
        repository.delete(taskCard);
    }

    @Override
    public Page<TaskCard> findAllForUser(User user, Pageable pageable) {
        return repository.findAllByUserIs(user, pageable);
    }

    @Override
    public TaskCard findTaskCardForUser(Long id, User user) {
        return repository.findByIdAndUserIs(id, user)
                         .orElseThrow(() -> new ResourceNotFoundException("Task card", "id", id));
    }
}
