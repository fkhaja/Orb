package com.sin.orb.service;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.repository.TaskCardRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    private final User userStub = new User();
    private final TaskCard cardStub = new TaskCard(1L, "test", LocalDate.now(), Collections.emptyList(),
                                                   userStub, "text", LocalDateTime.now(), true, true, "url");

    @Test
    void findAllForUserShouldReturnTaskCardList() {
        Page<TaskCard> stubPage = new PageImpl<>(List.of(cardStub));

        when(taskCardRepository.findAllByUserIs(any(User.class), any(Pageable.class))).thenReturn(stubPage);

        Page<TaskCard> resultPage = taskCardService.findAllForUser(userStub, Pageable.unpaged());

        verify(taskCardRepository).findAllByUserIs(any(User.class), any(Pageable.class));
        assertThat(resultPage.getTotalElements()).isEqualTo(1);
        assertThat(resultPage.getTotalPages()).isEqualTo(1);
    }

    @Test
    void findTaskCardByIdShouldReturnRequiredTaskCard() {
        when(taskCardRepository.findByIdAndUserIs(any(Long.class), any(User.class))).thenReturn(Optional.of(cardStub));

        TaskCard taskCard = taskCardService.findTaskCardForUser(1L, userStub);

        verify(taskCardRepository).findByIdAndUserIs(any(Long.class), any(User.class));
        assertThat(taskCard.getId()).isEqualTo(1L);
    }

    @Test
    void whenRepositoryDidNotFindRequiredCardThenThrowException() {
        when(taskCardRepository.findByIdAndUserIs(any(Long.class), any(User.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskCardService.findTaskCardForUser(1L, userStub))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");

        verify(taskCardRepository).findByIdAndUserIs(any(Long.class), any(User.class));
    }

    @Test
    void saveTaskCardShouldSetMissingFieldsAndSaveCard() {
        TaskCard card = new TaskCard();
        card.setName("test");

        when(taskCardRepository.save(any(TaskCard.class))).thenReturn(new TaskCard());
        taskCardService.saveTaskCard(card, userStub);

        verify(taskCardRepository).save(captor.capture());

        TaskCard result = captor.getValue();
        assertThat(result.getName()).isSameAs(card.getName());
        assertThat(result.getUser()).isNotNull();
        assertThat(result.getCreationDate()).isNotNull();
    }

    @Test
    void updateTaskCardShouldUpdateRequiredCard() {
        TaskCard replacement = new TaskCard();
        replacement.setName("updated");

        when(taskCardRepository.save(any(TaskCard.class))).thenReturn(new TaskCard());
        taskCardService.updateTaskCard(cardStub, replacement);

        verify(taskCardRepository).save(captor.capture());

        assertThat(captor.getValue().getName()).isSameAs(replacement.getName());
    }

    @Test
    void updateTaskCardShouldNotOverrideIdAndCreationDateAndTasks() {
        TaskCard replacement = new TaskCard();
        replacement.setName("updated");
        replacement.setId(0L);
        replacement.setCreationDate(LocalDate.MIN);
        replacement.setTasks(null);

        when(taskCardRepository.save(any(TaskCard.class))).thenReturn(new TaskCard());
        taskCardService.updateTaskCard(cardStub, replacement);

        verify(taskCardRepository).save(captor.capture());

        TaskCard result = captor.getValue();
        assertThat(result.getId()).isEqualTo(cardStub.getId());
        assertThat(result.getUser()).isSameAs(cardStub.getUser());
        assertThat(result.getCreationDate()).isSameAs(cardStub.getCreationDate());
        assertThat(result.getTasks()).isEqualTo(cardStub.getTasks());
        assertThat(result.getTerm()).isSameAs(cardStub.getTerm());
        assertThat(result.getDescription()).isSameAs(cardStub.getDescription());
        assertThat(result.getDone()).isSameAs(cardStub.getDone());
        assertThat(result.getImageUrl()).isSameAs(cardStub.getImageUrl());
    }

    @Test
    void deleteTaskCardShouldDeleteRequiredCard() {
        doNothing().when(taskCardRepository).delete(any(TaskCard.class));
        taskCardService.deleteTaskCard(cardStub);

        verify(taskCardRepository).delete(any(TaskCard.class));
    }
}