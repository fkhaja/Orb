package com.sin.orb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.dto.TaskCardDto;
import com.sin.orb.dto.TaskDto;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.service.TaskCardService;
import com.sin.orb.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskCardService taskCardService;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser
    void getAllTasksShouldReturnCorrectDtoList() throws Exception {
        Task firstStub = new Task(1L, "test", false, null);
        Task secondStub = new Task(2L, "task", false, null);
        List<Task> stubList = List.of(firstStub, secondStub);

        when(taskService.findAllTasks(any(Long.class), eq(null))).thenReturn(stubList);

        mockMvc.perform(get("/taskcards/1/tasks").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].taskId", equalTo(1)))
               .andExpect(jsonPath("$[0].value", equalTo("test")))
               .andExpect(jsonPath("$[0].completed", equalTo(false)))
               .andExpect(jsonPath("$[1].taskId", equalTo(2)))
               .andExpect(jsonPath("$[1].value", equalTo("task")))
               .andExpect(jsonPath("$[1].completed", equalTo(false)))
               .andExpect(status().isOk());

        verify(taskService).findAllTasks(any(Long.class), eq(null));
    }

    @Test
    @WithMockUser
    void getAllTasksShouldReturnStatusOk() throws Exception {
        when(taskService.findAllTasks(any(Long.class), eq(null))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/taskcards/1/tasks").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getTaskShouldReturnCorrectDto() throws Exception {
        Task taskStub = new Task(1L, "test", false, null);

        when(taskService.findTaskById(any(Long.class), any(Long.class), eq(null))).thenReturn(taskStub);

        mockMvc.perform(get("/taskcards/1/tasks/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.taskId", equalTo(1)))
               .andExpect(jsonPath("$.value", equalTo("test")))
               .andExpect(jsonPath("$.completed", equalTo(false)))
               .andExpect(status().isOk());

        verify(taskService).findTaskById(any(Long.class), any(Long.class), eq(null));
    }

    @Test
    @WithMockUser
    void getTaskShouldReturnStatusOk() throws Exception {
        when(taskService.findTaskById(any(Long.class), any(Long.class), eq(null))).thenReturn(mock(Task.class));

        mockMvc.perform(get("/taskcards/1/tasks/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenGetTaskGetsWrongIdThenReturnStatusNotFound() throws Exception {
        when(taskService.findTaskById(any(Long.class), any(Long.class), eq(null)))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/taskcards/1/tasks/0").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());

        verify(taskService).findTaskById(any(Long.class), any(Long.class), eq(null));
    }

    @Test
    @WithMockUser
    void createTaskShouldSaveTaskAndReturnCorrectDto() throws Exception {
        Task taskStub = new Task(1L, "test", false, null);
        TaskDto body = new TaskDto();
        body.setValue("test");

        when(taskService.saveTask(any(Task.class), any(TaskCard.class))).thenReturn(taskStub);
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenReturn(mock(TaskCard.class));

        mockMvc.perform(post("/taskcards/1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.taskId", equalTo(1)))
               .andExpect(jsonPath("$.value", equalTo("test")))
               .andExpect(jsonPath("$.completed", equalTo(false)))
               .andExpect(status().isCreated());

        verify(taskService).saveTask(any(Task.class), any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void createTaskShouldReturnStatusCreated() throws Exception {
        TaskDto body = new TaskDto();
        body.setValue("test");

        when(taskService.saveTask(any(Task.class), any(TaskCard.class))).thenReturn(mock(Task.class));

        mockMvc.perform(post("/taskcards/1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void createTaskWithEmptyBodyShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(post("/taskcards/1/tasks").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void whenCreateTaskGetsWrongCardIdThenDenySaveAndReturnStatusNotFound() throws Exception {
        TaskDto body = new TaskDto();
        body.setValue("test");

        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null)))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(post("/taskcards/0/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(status().isNotFound());

        verify(taskService, never()).saveTask(any(Task.class), any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void updateTaskShouldUpdateTaskAndReturnCorrectDto() throws Exception {
        Task existingStub = new Task(1L, "test", false, null);
        Task replacementStub = new Task(1L, "task", true, null);
        TaskDto body = new TaskDto();
        body.setValue("task");
        body.setCompleted(true);

        when(taskService.findTaskById(any(Long.class), any(Long.class), eq(null))).thenReturn(existingStub);
        when(taskService.updateTask(any(Task.class), any(Task.class))).thenReturn(replacementStub);

        mockMvc.perform(put("/taskcards/1/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.taskId", equalTo(1)))
               .andExpect(jsonPath("$.value", equalTo("task")))
               .andExpect(jsonPath("$.completed", equalTo(true)))
               .andExpect(status().isOk());

        verify(taskService).updateTask(any(Task.class), any(Task.class));
    }

    @Test
    @WithMockUser
    void updateTaskShouldReturnStatusOk() throws Exception {
        TaskDto body = new TaskDto();
        body.setValue("test");
        when(taskService.updateTask(any(Task.class), any(Task.class))).thenReturn(mock(Task.class));

        mockMvc.perform(put("/taskcards/1/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateTaskWithEmptyBodyShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(put("/taskcards/1/tasks/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void whenUpdateTaskGetsWrongIdThenDenyUpdateAndReturnStatusNotFound() throws Exception {
        TaskDto body = new TaskDto();
        body.setValue("test");

        when(taskService.findTaskById(any(Long.class), any(Long.class), eq(null)))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/taskcards/1/tasks/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(status().isNotFound());

        verify(taskService, never()).updateTask(any(Task.class), any(Task.class));
    }

    @Test
    @WithMockUser
    void deleteTaskShouldDeleteTask() throws Exception {
        when(taskService.findTaskById(any(Long.class), any(Long.class), eq(null))).thenReturn(mock(Task.class));

        mockMvc.perform(delete("/taskcards/1/tasks/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());

        verify(taskService).deleteTask(any(Task.class));
    }

    @Test
    @WithMockUser
    void deleteTaskShouldReturnStatusNoContent() throws Exception {
        mockMvc.perform(delete("/taskcards/1/tasks/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void whenDeleteTaskGetsWrongIdThenDenyDeleteAndReturnStatusNotFound() throws Exception {
        when(taskService.findTaskById(any(Long.class), any(Long.class), eq(null)))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(delete("/taskcards/1/tasks/0").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());

        verify(taskService, never()).deleteTask(any(Task.class));
    }

    @Test
    @WithMockUser
    void patchTaskShouldUpdatePassedFields() throws Exception {
        TaskDto body = new TaskDto();
        body.setValue("test");

        Task result = new Task();
        result.setValue("test");

        when(taskService.findTaskById(any(Long.class), anyLong(), eq(null))).thenReturn(mock(Task.class));
        when(taskService.partlyUpdateTask(any(Task.class), anyMap())).thenReturn(result);

        mockMvc.perform(patch("/taskcards/1/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.value", equalTo("test")));

        verify(taskService).partlyUpdateTask(any(Task.class), anyMap());
    }

    @Test
    @WithMockUser
    void patchTaskShouldReturnStatusOk() throws Exception {
        when(taskService.findTaskById(any(Long.class), anyLong(), eq(null))).thenReturn(mock(Task.class));
        when(taskService.partlyUpdateTask(any(Task.class), anyMap())).thenReturn(mock(Task.class));

        mockMvc.perform(patch("/taskcards/1/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(new TaskCardDto())))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenPatchTaskGetsWrongIdThenDenyUpdateAndReturnStatusNotFound() throws Exception {
        when(taskService.findTaskById(any(Long.class), anyLong(), eq(null))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(patch("/taskcards/0/tasks/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(new TaskCardDto())))
               .andExpect(status().isNotFound());

        verify(taskService, never()).partlyUpdateTask(any(Task.class), anyMap());
    }
}