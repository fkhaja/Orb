package com.sin.orb.repository;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByTaskCardIdAndTaskCardUserIs(Long id, User user);

    Optional<Task> findByIdAndTaskCardIdAndTaskCardUserIs(Long id, Long cardId, User user);
}