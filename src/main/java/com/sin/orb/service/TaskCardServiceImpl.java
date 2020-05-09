package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.repo.TaskCardRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class TaskCardServiceImpl implements TaskCardService {
    private TaskCardRepository repository;

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
        BeanUtils.copyProperties(replacement, existing, "id", "user", "creationDate");
        return repository.save(existing);
    }

    @Override
    public void deleteTaskCard(TaskCard taskCard) {
        repository.delete(taskCard);
    }
}
