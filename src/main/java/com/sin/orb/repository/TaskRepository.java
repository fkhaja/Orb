package com.sin.orb.repository;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

    Page<Task> findAllByTaskCardIdAndTaskCardUserIs(Long id, User user, Pageable pageable);

    Optional<Task> findByIdAndTaskCardIdAndTaskCardUserIs(Long id, Long cardId, User user);
}
