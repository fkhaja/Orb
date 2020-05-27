package com.sin.orb.repository;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskCardRepository extends JpaRepository<TaskCard, Long> {

    @EntityGraph(attributePaths = {"tasks"})
    List<TaskCard> findAllByUserIs(User user);

    @EntityGraph(attributePaths = {"tasks"})
    Optional<TaskCard> findByIdAndUserIs(Long id, User user);
}
