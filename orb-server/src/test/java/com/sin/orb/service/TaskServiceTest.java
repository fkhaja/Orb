package com.sin.orb.service;

import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.domain.User;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.repository.TaskRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private static Map<String, Object> defaultUpdates;

    private User userStub;

    private TaskCard cardStub;

    private List<Task> taskStubs;

    @BeforeAll
    public static void setUp() {
        defaultUpdates = new HashMap<>(2);
        defaultUpdates.put("value", "value");
        defaultUpdates.put("completed", true);
    }

    @BeforeEach
    void init() {
        Task firstStub = new Task();
        firstStub.setValue("value_1");
        firstStub.setCompleted(true);

        Task secondStub = new Task();
        secondStub.setValue("value_2");
        secondStub.setCompleted(true);

        taskStubs = List.of(firstStub, secondStub);

        userStub = new User();
        cardStub = new TaskCard();
    }

    @Test
    void findAllTasksShouldReturnTaskList() {
        when(taskRepository.findAllByTaskCardIdAndTaskCardUserIs(anyLong(), any(User.class))).thenReturn(taskStubs);

        List<Task> resultList = taskService.findAllTasks(1L, userStub);

        assertThat(resultList.size()).isEqualTo(2);
        assertThat(resultList.get(0)).isSameAs(taskStubs.get(0));
        assertThat(resultList.get(1)).isSameAs(taskStubs.get(1));

        verify(taskRepository).findAllByTaskCardIdAndTaskCardUserIs(anyLong(), any(User.class));
    }

    @Test
    void findTaskByIdShouldReturnRequiredTask() {
        when(taskRepository.findByIdAndTaskCardIdAndTaskCardUserIs(anyLong(), anyLong(), any(User.class)))
                .thenReturn(Optional.of(taskStubs.get(0)));

        Task result = taskService.findTaskById(1L, 1L, userStub);

        assertThat(result).isNotNull();

        verify(taskRepository).findByIdAndTaskCardIdAndTaskCardUserIs(anyLong(), anyLong(), any(User.class));
    }

    @Test
    void whenRepositoryDidNotFindRequiredTaskThenThrowException() {
        when(taskRepository.findByIdAndTaskCardIdAndTaskCardUserIs(anyLong(), anyLong(), any(User.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findTaskById(0L, 0L, userStub))
                .isExactlyInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void saveTaskShouldSavePassedTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(taskStubs.get(0));
        taskService.saveTask(taskStubs.get(1), cardStub);

        verify(taskRepository).save(captor.capture());

        Task result = captor.getValue();
        assertThat(result).isNotNull();
        assertThat(result.getValue()).isEqualTo("value_2");
        assertThat(result.isCompleted()).isEqualTo(true);
        assertThat(result.getTaskCard()).isNotNull();
    }

    @Test
    void updateTaskShouldCorrectlyUpdateRequiredTask() {
        Task replacement = taskStubs.get(1);
        Task result = taskService.updateTask(taskStubs.get(0), taskStubs.get(1));

        assertThat(result).isNotNull();
        assertThat(result.getValue()).isSameAs(replacement.getValue());
        assertThat(result.isCompleted()).isSameAs(replacement.isCompleted());
    }

    @Test
    void updateTaskShouldNotOverrideId() {
        Task existing = new Task();
        existing.setId(1L);
        existing.setTaskCard(cardStub);

        Task replacement = new Task();
        replacement.setId(0L);
        replacement.setTaskCard(null);

        Task result = taskService.updateTask(existing, replacement);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(existing.getId());
        assertThat(result.getTaskCard()).isEqualTo(existing.getTaskCard());
    }

    @Test
    void deleteTaskShouldDeleteRequiredTask() {
        doNothing().when(taskRepository).delete(any(Task.class));
        taskService.deleteTask(taskStubs.get(0));

        verify(taskRepository).delete(any(Task.class));
    }

    @Test
    void partlyUpdateTaskShouldUpdateRequiredTask() {
        Task result = taskService.partlyUpdateTask(taskStubs.get(0), defaultUpdates);

        assertThat(result.getValue()).isSameAs(defaultUpdates.get("value"));
        assertThat(result.isCompleted()).isSameAs(defaultUpdates.get("completed"));
    }

    @Test
    void partlyUpdateTaskShouldNotOverrideSpecifiedFields() {
        Task existing = new Task();
        existing.setId(1L);
        existing.setTaskCard(cardStub);

        Map<String, Object> updates = new HashMap<>(defaultUpdates);
        updates.put("id", 0L);
        updates.put("taskCard", null);

        Task result = taskService.partlyUpdateTask(existing, updates);

        assertThat(result.getId()).isEqualTo(existing.getId());
        assertThat(result.getTaskCard()).isSameAs(existing.getTaskCard());
    }
}