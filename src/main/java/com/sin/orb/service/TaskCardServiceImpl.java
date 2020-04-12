package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.exceptions.NotFoundException;
import com.sin.orb.repo.TaskCardRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCardServiceImpl implements TaskCardService {
    private TaskCardRepository repository;

    @Autowired
    public TaskCardServiceImpl(TaskCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskCard saveTaskCard(TaskCard taskCard) {
        return repository.save(taskCard);
    }

    @Override
    public List<TaskCard> findAllTaskCards() {
        return repository.findAll();
    }

    @Override
    public TaskCard findTaskCardById(Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public TaskCard updateTaskCard(TaskCard existing, TaskCard replacement) {
        BeanUtils.copyProperties(replacement, existing, "id");
        return repository.save(existing);
    }

    @Override
    public void deleteTaskCard(TaskCard taskCard) {
        repository.delete(taskCard);
    }
}
