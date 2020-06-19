package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.repository.TaskCardRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskCardServiceTest {

    @MockBean
    private TaskCardRepository taskCardRepository;

    @Autowired
    private TaskCardService taskCardService;

    @Captor
    private ArgumentCaptor<TaskCard> captor;

    private static Map<String, Object> defaultUpdates;

    private List<TaskCard> cardStubs;

    private User userStub;

    @BeforeAll
    public static void setUp() {
        defaultUpdates = new HashMap<>(6);
        defaultUpdates.put("title", "title");
        defaultUpdates.put("description", "description");
        defaultUpdates.put("imageUrl", "url");
        defaultUpdates.put("completedAtTerm", true);
        defaultUpdates.put("done", true);
        defaultUpdates.put("term", "2020-06-18T12:00");
    }

    @BeforeEach
    void init() {
        TaskCard firstStub = new TaskCard();
        firstStub.setTitle("title_1");
        firstStub.setDescription("description_1");
        firstStub.setImageUrl("url_1");
        firstStub.setCompletedAtTerm(true);
        firstStub.setDone(true);
        firstStub.setTerm(LocalDateTime.now());

        TaskCard secondStub = new TaskCard();
        secondStub.setTitle("title_2");
        secondStub.setDescription("description_2");
        secondStub.setImageUrl("url_2");
        secondStub.setCompletedAtTerm(true);
        secondStub.setDone(true);
        secondStub.setTerm(LocalDateTime.now());

        cardStubs = List.of(firstStub, secondStub);

        userStub = new User();
    }

    @Test
    void findAllForUserShouldReturnTaskCardList() {
        Page<TaskCard> page = new PageImpl<>(cardStubs);

        when(taskCardRepository.findAllByUserIs(any(User.class), any(Pageable.class))).thenReturn(page);

        Page<TaskCard> resultPage = taskCardService.findAllForUser(userStub, Pageable.unpaged());

        assertThat(resultPage.getTotalElements()).isEqualTo(2);
        assertThat(resultPage.getContent().get(0)).isSameAs(cardStubs.get(0));
        assertThat(resultPage.getContent().get(1)).isSameAs(cardStubs.get(1));

        verify(taskCardRepository).findAllByUserIs(any(User.class), any(Pageable.class));
    }

    @Test
    void findTaskCardByIdShouldReturnRequiredTaskCard() {
        when(taskCardRepository.findByIdAndUserIs(anyLong(), any(User.class))).thenReturn(
                Optional.of(cardStubs.get(0)));

        TaskCard taskCard = taskCardService.findTaskCardForUser(1L, userStub);

        assertThat(taskCard.getTitle()).isEqualTo("title_1");
        assertThat(taskCard.getDescription()).isEqualTo("description_1");
        assertThat(taskCard.getImageUrl()).isEqualTo("url_1");
        assertThat(taskCard.getTerm()).isNotNull();
        assertThat(taskCard.isDone()).isEqualTo(true);
        assertThat(taskCard.isCompletedAtTerm()).isEqualTo(true);

        verify(taskCardRepository).findByIdAndUserIs(anyLong(), any(User.class));
    }

    @Test
    void whenRepositoryDidNotFindRequiredCardThenThrowException() {
        when(taskCardRepository.findByIdAndUserIs(anyLong(), any(User.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskCardService.findTaskCardForUser(1L, userStub))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");

        verify(taskCardRepository).findByIdAndUserIs(anyLong(), any(User.class));
    }

    @Test
    void saveTaskCardShouldSaveCard() {
        when(taskCardRepository.save(any(TaskCard.class))).thenReturn(cardStubs.get(0));
        taskCardService.saveTaskCard(cardStubs.get(1), userStub);

        verify(taskCardRepository).save(captor.capture());

        TaskCard result = captor.getValue();
        assertThat(result.getTitle()).isEqualTo("title_2");
        assertThat(result.getDescription()).isEqualTo("description_2");
        assertThat(result.getImageUrl()).isEqualTo("url_2");
        assertThat(result.getTerm()).isNotNull();
        assertThat(result.isDone()).isEqualTo(true);
        assertThat(result.isCompletedAtTerm()).isEqualTo(true);
    }

    @Test
    void saveTaskCardShouldSetMissingFields() {
        when(taskCardRepository.save(any(TaskCard.class))).thenReturn(cardStubs.get(0));
        taskCardService.saveTaskCard(cardStubs.get(1), userStub);

        verify(taskCardRepository).save(captor.capture());

        TaskCard result = captor.getValue();
        assertThat(result.getUser()).isNotNull();
        assertThat(result.getCreationDate()).isNotNull();
        assertThat(result.getTasks()).isNotNull();
    }

    @Test
    void updateTaskCardShouldUpdateRequiredCard() {
        TaskCard replacement = cardStubs.get(1);
        TaskCard result = taskCardService.updateTaskCard(cardStubs.get(0), replacement);

        assertThat(result.getTitle()).isSameAs(replacement.getTitle());
        assertThat(result.getDescription()).isSameAs(replacement.getDescription());
        assertThat(result.getImageUrl()).isSameAs(replacement.getImageUrl());
        assertThat(result.getTerm()).isSameAs(replacement.getTerm());
        assertThat(result.isDone()).isSameAs(replacement.isDone());
        assertThat(result.isCompletedAtTerm()).isSameAs(replacement.isCompletedAtTerm());
    }

    @Test
    void updateTaskCardShouldNotOverrideSpecifiedFields() {
        TaskCard existing = cardStubs.get(0);
        existing.setId(1L);
        existing.setCreationDate(LocalDate.now());
        existing.setUser(userStub);
        existing.setTasks(Collections.emptyList());

        TaskCard replacement = cardStubs.get(1);
        existing.setId(0L);
        existing.setCreationDate(LocalDate.MIN);
        existing.setUser(null);
        existing.setTasks(null);

        TaskCard result = taskCardService.updateTaskCard(existing, replacement);

        assertThat(result.getId()).isEqualTo(existing.getId());
        assertThat(result.getUser()).isSameAs(existing.getUser());
        assertThat(result.getCreationDate()).isSameAs(existing.getCreationDate());
        assertThat(result.getTasks()).isEqualTo(existing.getTasks());
    }

    @Test
    void deleteTaskCardShouldDeleteRequiredCard() {
        doNothing().when(taskCardRepository).delete(any(TaskCard.class));
        taskCardService.deleteTaskCard(cardStubs.get(0));

        verify(taskCardRepository).delete(any(TaskCard.class));
    }

    @Test
    void partlyUpdateTaskCardShouldUpdateRequiredCard() {
        TaskCard result = taskCardService.partlyUpdateTaskCard(cardStubs.get(0), defaultUpdates);

        assertThat(result.getTitle()).isSameAs(defaultUpdates.get("title"));
        assertThat(result.getDescription()).isSameAs(defaultUpdates.get("description"));
        assertThat(result.getImageUrl()).isSameAs(defaultUpdates.get("imageUrl"));
        assertThat(result.getTerm()).isSameAs(defaultUpdates.get("term"));
        assertThat(result.isDone()).isSameAs(defaultUpdates.get("done"));
        assertThat(result.isCompletedAtTerm()).isSameAs(defaultUpdates.get("completedAtTerm"));
    }

    @Test
    void partlyUpdateTaskCardShouldNotOverrideSpecifiedFields() {
        TaskCard existing = cardStubs.get(0);
        existing.setId(1L);
        existing.setCreationDate(LocalDate.now());
        existing.setUser(userStub);
        existing.setTasks(Collections.emptyList());

        Map<String, Object> updates = new HashMap<>(defaultUpdates);
        updates.put("id", 0L);
        updates.put("creationDate", LocalDate.MIN);
        updates.put("user", null);
        updates.put("tasks", null);

        TaskCard result = taskCardService.partlyUpdateTaskCard(existing, updates);

        assertThat(result.getId()).isEqualTo(existing.getId());
        assertThat(result.getUser()).isSameAs(existing.getUser());
        assertThat(result.getCreationDate()).isSameAs(existing.getCreationDate());
        assertThat(result.getTasks()).isEqualTo(existing.getTasks());
    }
}