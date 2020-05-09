package com.sin.orb.repository;

import com.sin.orb.domain.TaskCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCardRepository extends JpaRepository<TaskCard, Long> {

    List<TaskCard> findAllByUserIdEquals(Long userId);

    TaskCard findByIdEqualsAndUserId(Long id, Long userId);

}
