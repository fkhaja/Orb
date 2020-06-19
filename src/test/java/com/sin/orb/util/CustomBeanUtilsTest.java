package com.sin.orb.util;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomBeanUtilsTest {
    @Test
    void populateThrowsNullPointerWhenGetsNull() {
        assertThrows(NullPointerException.class, () -> CustomBeanUtils.populate(null, null, null));
    }

    @Test
    void populateCorrectlyCopyProps() {
        TaskCard taskCard = new TaskCard();
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "title");
        updates.put("description", "description");
        updates.put("done", true);
        updates.put("completedAtTerm", true);
        updates.put("imageUrl", "url");

        CustomBeanUtils.populate(taskCard, updates, TaskCard.class);

        assertThat(taskCard.getTitle()).isEqualTo(updates.get("title"));
        assertThat(taskCard.getDescription()).isEqualTo(updates.get("description"));
        assertThat(taskCard.isDone()).isEqualTo(updates.get("done"));
        assertThat(taskCard.isCompletedAtTerm()).isEqualTo(updates.get("completedAtTerm"));
        assertThat(taskCard.getImageUrl()).isEqualTo(updates.get("imageUrl"));
    }

    @Test
    void populateCorrectlyIgnoresPassedProps() {
        TaskCard taskCard = new TaskCard();
        taskCard.setId(1L);
        taskCard.setUser(new User());
        taskCard.setTasks(Collections.emptyList());
        taskCard.setCreationDate(LocalDate.now());
        Map<String, Object> updates = new HashMap<>();
        updates.put("id", 0L);
        updates.put("user", null);
        updates.put("tasks", null);
        updates.put("creationDate", "2020-02-02T12:00");

        CustomBeanUtils.populate(taskCard, updates, TaskCard.class, "id", "user", "tasks", "creationDate");

        assertThat(updates.get("id")).isNotEqualTo(taskCard.getId());
        assertThat(updates.get("user")).isNotEqualTo(taskCard.getUser());
        assertThat(updates.get("tasks")).isNotEqualTo(taskCard.getTasks());
        assertThat(updates.get("creationDate")).isNotEqualTo(taskCard.getCreationDate());
    }
}