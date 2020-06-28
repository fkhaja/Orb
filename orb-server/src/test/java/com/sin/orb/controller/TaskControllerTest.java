package com.sin.orb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sin.orb.domain.Task;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.dto.TaskDto;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.service.TaskCardService;
import com.sin.orb.service.TaskService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
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

    private static List<Task> taskStubs;

    private static TaskDto defaultBody;

    @BeforeAll
    public static void setUp() {
        Task firstStub = new Task();
        firstStub.setId(1L);
        firstStub.setValue("test_1");
        firstStub.setCompleted(true);

        Task secondStub = new Task();
        secondStub.setId(2L);
        secondStub.setValue("test_2");
        secondStub.setCompleted(true);

        taskStubs = List.of(firstStub, secondStub);

        defaultBody = new TaskDto();
        defaultBody.setValue("test");
        defaultBody.setCompleted(true);
    }

    @Test
    @WithMockUser
    void getAllTasksShouldReturnCorrectDtoList() throws Exception {
        when(taskService.findAllTasks(anyLong(), any())).thenReturn(taskStubs);

        mockMvc.perform(get("/taskcards/1/tasks").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].taskId", equalTo(1)))
               .andExpect(jsonPath("$[0].value", equalTo("test_1")))
               .andExpect(jsonPath("$[0].completed", equalTo(true)))
               .andExpect(jsonPath("$[1].taskId", equalTo(2)))
               .andExpect(jsonPath("$[1].value", equalTo("test_2")))
               .andExpect(jsonPath("$[1].completed", equalTo(true)))
               .andExpect(status().isOk());

        verify(taskService).findAllTasks(anyLong(), any());
    }

    @Test
    @WithMockUser
    void getAllTasksShouldReturnStatusOk() throws Exception {
        when(taskService.findAllTasks(anyLong(), any())).thenReturn(taskStubs);

        mockMvc.perform(get("/taskcards/1/tasks").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getTaskShouldReturnCorrectDto() throws Exception {
        when(taskService.findTaskById(anyLong(), anyLong(), any())).thenReturn(taskStubs.get(0));

        mockMvc.perform(get("/taskcards/1/tasks/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.taskId", equalTo(1)))
               .andExpect(jsonPath("$.value", equalTo("test_1")))
               .andExpect(jsonPath("$.completed", equalTo(true)))
               .andExpect(status().isOk());

        verify(taskService).findTaskById(anyLong(), anyLong(), any());
    }

    @Test
    @WithMockUser
    void getTaskShouldReturnStatusOk() throws Exception {
        when(taskService.findTaskById(anyLong(), anyLong(), any())).thenReturn(taskStubs.get(0));

        mockMvc.perform(get("/taskcards/1/tasks/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenGetTaskGetsWrongIdThenReturnStatusNotFound() throws Exception {
        when(taskService.findTaskById(anyLong(), anyLong(), any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/taskcards/1/tasks/0").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());

        verify(taskService).findTaskById(anyLong(), anyLong(), any());
    }

    @Test
    @WithMockUser
    void createTaskShouldSaveTaskAndReturnCorrectDto() throws Exception {
        when(taskService.saveTask(any(Task.class), any(TaskCard.class))).thenReturn(taskStubs.get(0));
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenReturn(mock(TaskCard.class));

        mockMvc.perform(post("/taskcards/1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.taskId", equalTo(1)))
               .andExpect(jsonPath("$.value", equalTo("test_1")))
               .andExpect(jsonPath("$.completed", equalTo(true)))
               .andExpect(status().isCreated());

        verify(taskService).saveTask(any(Task.class), any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void createTaskShouldReturnStatusCreated() throws Exception {
        when(taskService.saveTask(any(Task.class), any(TaskCard.class))).thenReturn(taskStubs.get(0));

        mockMvc.perform(post("/taskcards/1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
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
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(post("/taskcards/0/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(status().isNotFound());

        verify(taskService, never()).saveTask(any(Task.class), any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void updateTaskShouldUpdateTaskAndReturnCorrectDto() throws Exception {
        when(taskService.findTaskById(anyLong(), anyLong(), any())).thenReturn(taskStubs.get(0));
        when(taskService.updateTask(any(Task.class), any(Task.class))).thenReturn(taskStubs.get(1));

        mockMvc.perform(put("/taskcards/1/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.taskId", equalTo(2)))
               .andExpect(jsonPath("$.value", equalTo("test_2")))
               .andExpect(jsonPath("$.completed", equalTo(true)))
               .andExpect(status().isOk());

        verify(taskService).updateTask(any(Task.class), any(Task.class));
    }

    @Test
    @WithMockUser
    void updateTaskShouldReturnStatusOk() throws Exception {
        when(taskService.updateTask(any(Task.class), any(Task.class))).thenReturn(taskStubs.get(0));

        mockMvc.perform(put("/taskcards/1/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
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
        when(taskService.findTaskById(anyLong(), anyLong(), any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/taskcards/1/tasks/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(status().isNotFound());

        verify(taskService, never()).updateTask(any(Task.class), any(Task.class));
    }

    @Test
    @WithMockUser
    void deleteTaskShouldDeleteTask() throws Exception {
        when(taskService.findTaskById(anyLong(), anyLong(), any())).thenReturn(taskStubs.get(0));

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
        when(taskService.findTaskById(anyLong(), anyLong(), any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(delete("/taskcards/1/tasks/0").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());

        verify(taskService, never()).deleteTask(any(Task.class));
    }

    @Test
    @WithMockUser
    void patchTaskShouldUpdatePassedFields() throws Exception {
        when(taskService.findTaskById(anyLong(), anyLong(), any())).thenReturn(taskStubs.get(0));
        when(taskService.partlyUpdateTask(any(Task.class), anyMap())).thenReturn(taskStubs.get(1));

        mockMvc.perform(patch("/taskcards/1/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.value", equalTo("test_2")))
               .andExpect(jsonPath("$.completed", equalTo(true)));

        verify(taskService).partlyUpdateTask(any(Task.class), anyMap());
    }

    @Test
    @WithMockUser
    void patchTaskShouldReturnStatusOk() throws Exception {
        when(taskService.findTaskById(anyLong(), anyLong(), any())).thenReturn(taskStubs.get(0));
        when(taskService.partlyUpdateTask(any(Task.class), anyMap())).thenReturn(taskStubs.get(1));

        mockMvc.perform(patch("/taskcards/1/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenPatchTaskGetsWrongIdThenDenyUpdateAndReturnStatusNotFound() throws Exception {
        when(taskService.findTaskById(anyLong(), anyLong(), any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(patch("/taskcards/0/tasks/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(status().isNotFound());

        verify(taskService, never()).partlyUpdateTask(any(Task.class), anyMap());
    }
}