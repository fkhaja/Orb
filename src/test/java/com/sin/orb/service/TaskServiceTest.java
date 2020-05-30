package com.sin.orb.service;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceTest {

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Captor
    private ArgumentCaptor<Task> captor;

    private final User userStub = new User();
    private final Task taskStub = new Task(1L, "value", false, null);

    @Test
    void findAllTasksShouldReturnTaskList() {
        Page<Task> stubPage = new PageImpl<>(List.of(taskStub));

        when(taskRepository.findAllByTaskCardIdAndTaskCardUserIs(any(Long.class), any(User.class), any(Pageable.class)))
                .thenReturn(stubPage);

        Page<Task> resultPage = taskService.findAllTasks(1L, userStub, Pageable.unpaged());
        verify(taskRepository).findAllByTaskCardIdAndTaskCardUserIs(any(Long.class), any(User.class), any(Pageable.class));
        assertThat(resultPage.getTotalElements()).isEqualTo(1);
        assertThat(resultPage.getTotalPages()).isEqualTo(1);
    }

    @Test
    void findTaskByIdShouldReturnRequiredTask() {
        when(taskRepository.findByIdAndTaskCardIdAndTaskCardUserIs(any(Long.class), any(Long.class), any(User.class)))
                .thenReturn(Optional.of(taskStub));

        Task result = taskService.findTaskById(1L, 1L, userStub);

        verify(taskRepository).findByIdAndTaskCardIdAndTaskCardUserIs(any(Long.class), any(Long.class),
                                                                      any(User.class));
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void whenRepositoryDidNotFindRequiredTaskThenThrowException() {
        when(taskRepository.findByIdAndTaskCardIdAndTaskCardUserIs(any(Long.class), any(Long.class), any(User.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findTaskById(0L, 0L, userStub))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void saveTaskShouldSavePassedTask() {
        Task task = new Task();
        task.setValue("test");

        when(taskRepository.save(any(Task.class))).thenReturn(new Task());
        taskService.saveTask(task, mock(TaskCard.class));

        verify(taskRepository).save(captor.capture());

        Task result = captor.getValue();
        assertThat(result).isNotNull();
        assertThat(result.getValue()).isSameAs(task.getValue());
        assertThat(result.getTaskCard()).isNotNull();
    }

    @Test
    void updateTaskCardShouldUpdateRequiredCard() {
        Task replacement = new Task();
        replacement.setValue("updated");
        replacement.setCompleted(true);

        when(taskRepository.save(any(Task.class))).thenReturn(new Task());
        taskService.updateTask(taskStub, replacement);

        verify(taskRepository).save(captor.capture());

        Task result = captor.getValue();
        assertThat(result).isNotNull();
        assertThat(result.getValue()).isSameAs(replacement.getValue());
        assertThat(result.getCompleted()).isSameAs(replacement.getCompleted());
    }

    @Test
    void updateTaskShouldNotOverrideId() {
        Task replacement = new Task();
        replacement.setId(0L);

        when(taskRepository.save(any(Task.class))).thenReturn(new Task());
        taskService.updateTask(taskStub, replacement);

        verify(taskRepository).save(captor.capture());

        Task result = captor.getValue();
        assertThat(result.getId()).isEqualTo(taskStub.getId());
    }

    @Test
    void deleteTaskCardShouldDeleteRequiredCard() {
        doNothing().when(taskRepository).delete(any(Task.class));
        taskService.deleteTask(taskStub);

        verify(taskRepository).delete(any(Task.class));
    }
}