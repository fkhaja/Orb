package com.sin.orb.repo;

import com.sin.orb.domain.TaskCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCardRepository extends JpaRepository<TaskCard, Long> {
}
