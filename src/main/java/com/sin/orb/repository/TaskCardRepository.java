package com.sin.orb.repository;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TaskCardRepository extends PagingAndSortingRepository<TaskCard, Long> {

    @EntityGraph(attributePaths = {"tasks"})
    Page<TaskCard> findAllByUserIs(User user, Pageable pageable);

    @EntityGraph(attributePaths = {"tasks"})
    Optional<TaskCard> findByIdAndUserIs(Long id, User user);
}
