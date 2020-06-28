package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.repository.TaskCardRepository;
import com.sin.orb.util.CustomBeanUtils;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

@Service
public class TaskCardServiceImpl implements TaskCardService {
    private final TaskCardRepository repository;

    @Autowired
    public TaskCardServiceImpl(TaskCardRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public TaskCard saveTaskCard(@NonNull TaskCard taskCard, User user) {
        taskCard.setUser(user);
        taskCard.setCreationDate(LocalDate.now());
        taskCard.setTasks(new ArrayList<>());

        return repository.save(taskCard);
    }

    @Override
    @Transactional
    public TaskCard updateTaskCard(@NonNull TaskCard existing, @NonNull TaskCard replacement) {
        BeanUtils.copyProperties(replacement, existing, "id", "user", "creationDate", "tasks");
        return existing;
    }

    @Override
    @Transactional
    public void deleteTaskCard(@NonNull TaskCard taskCard) {
        repository.delete(taskCard);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskCard> findAllForUser(User user, Pageable pageable) {
        return repository.findAllByUserIs(user, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskCard findTaskCardForUser(Long id, User user) {
        return repository.findByIdAndUserIs(id, user)
                         .orElseThrow(() -> new ResourceNotFoundException("Task card", "id", id));
    }

    @Override
    @Transactional
    public TaskCard partlyUpdateTaskCard(@NonNull TaskCard card, @NonNull Map<String, Object> updates) {
        String[] ignoreProps = {"id", "creationDate", "tasks", "user"};

        if (updates.containsKey("term")) {
            String term = String.valueOf(updates.get("term"));
            updates.put("term", LocalDateTime.parse(term, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        }

        CustomBeanUtils.populate(card, updates, TaskCard.class, ignoreProps);
        return card;
    }
}