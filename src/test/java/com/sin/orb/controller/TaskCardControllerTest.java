package com.sin.orb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.dto.TaskCardDto;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.service.TaskCardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskCardService taskCardService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser
    void getAllTaskCardsShouldReturnCorrectDtoList() throws Exception {
        TaskCard firstStub = new TaskCard(1L, "test", LocalDate.now(), Collections.emptyList(), null);
        TaskCard secondStub = new TaskCard(2L, "card", LocalDate.now(), Collections.emptyList(), null);
        List<TaskCard> stubList = List.of(firstStub, secondStub);

        when(taskCardService.findAllForUser(eq(null))).thenReturn(stubList);

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].cardId", equalTo(1)))
               .andExpect(jsonPath("$[0].name", equalTo("test")))
               .andExpect(jsonPath("$[0].creationDate", notNullValue()))
               .andExpect(jsonPath("$[0].tasks", hasSize(0)))
               .andExpect(jsonPath("$[1].cardId", equalTo(2)))
               .andExpect(jsonPath("$[1].name", equalTo("card")))
               .andExpect(jsonPath("$[1].creationDate", notNullValue()))
               .andExpect(jsonPath("$[1].tasks", hasSize(0)))
               .andExpect(status().isOk());

        verify(taskCardService).findAllForUser(eq(null));
    }

    @Test
    @WithMockUser
    void getAllTaskCardsShouldReturnStatusOk() throws Exception {
        when(taskCardService.findAllForUser(eq(null))).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getTaskCardShouldReturnCorrectDto() throws Exception {
        TaskCard cardStub = new TaskCard(1L, "test", LocalDate.now(), Collections.emptyList(), null);

        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenReturn(cardStub);

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.cardId", equalTo(1)))
               .andExpect(jsonPath("$.name", equalTo("test")))
               .andExpect(jsonPath("$.creationDate", notNullValue()))
               .andExpect(jsonPath("$.tasks", hasSize(0)))
               .andExpect(status().isOk());

        verify(taskCardService).findTaskCardForUser(any(Long.class), eq(null));
    }

    @Test
    @WithMockUser
    void getTaskCardShouldReturnStatusOk() throws Exception {
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenReturn(mock(TaskCard.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenGetTaskCardGetsWrongIdThenReturnStatusNotFound() throws Exception {
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards/0").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());

        verify(taskCardService).findTaskCardForUser(any(Long.class), eq(null));
    }

    @Test
    @WithMockUser
    void createTaskCardShouldSaveCardAndReturnCorrectDto() throws Exception {
        TaskCard cardStub = new TaskCard(1L, "test", LocalDate.now(), Collections.emptyList(), null);
        TaskCardDto body = new TaskCardDto();
        body.setName("test");

        when(taskCardService.saveTaskCard(any(TaskCard.class), eq(null))).thenReturn(cardStub);

        mockMvc.perform(post("/taskcards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.cardId", equalTo(1)))
               .andExpect(jsonPath("$.name", equalTo("test")))
               .andExpect(jsonPath("$.creationDate", notNullValue()))
               .andExpect(jsonPath("$.tasks", hasSize(0)))
               .andExpect(status().isCreated());

        verify(taskCardService).saveTaskCard(any(TaskCard.class), eq(null));
    }

    @Test
    @WithMockUser
    void createTaskCardShouldReturnStatusCreated() throws Exception {
        TaskCardDto body = new TaskCardDto();

        when(taskCardService.saveTaskCard(any(TaskCard.class), eq(null))).thenReturn(mock(TaskCard.class));

        mockMvc.perform(post("/taskcards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void createTaskCardWithEmptyBodyShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(post("/taskcards").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void updateTaskCardShouldUpdateCardAndReturnCorrectDto() throws Exception {
        TaskCard existingStub = new TaskCard(1L, "test", LocalDate.now(), Collections.emptyList(), null);
        TaskCard replacementStub = new TaskCard(1L, "card", LocalDate.now(), Collections.emptyList(), null);
        TaskCardDto body = new TaskCardDto();
        body.setName("card");

        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenReturn(existingStub);
        when(taskCardService.updateTaskCard(any(TaskCard.class), any(TaskCard.class))).thenReturn(replacementStub);

        mockMvc.perform(put("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.cardId", equalTo(1)))
               .andExpect(jsonPath("$.name", equalTo("card")))
               .andExpect(jsonPath("$.creationDate", notNullValue()))
               .andExpect(jsonPath("$.tasks", hasSize(0)))
               .andExpect(status().isOk());

        verify(taskCardService).updateTaskCard(any(TaskCard.class), any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void updateTaskCardShouldReturnStatusOk() throws Exception {
        TaskCardDto body = new TaskCardDto();

        when(taskCardService.updateTaskCard(any(TaskCard.class), any(TaskCard.class))).thenReturn(mock(TaskCard.class));

        mockMvc.perform(put("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateTaskCardWithEmptyBodyShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(put("/taskcards/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void whenUpdateTaskCardGetsWrongIdThenDenyUpdateAndReturnStatusNotFound() throws Exception {
        TaskCardDto body = new TaskCardDto();

        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/taskcards/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(status().isNotFound());

        verify(taskCardService, never()).updateTaskCard(any(TaskCard.class), any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void deleteTaskCardShouldDeleteCard() throws Exception {
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenReturn(mock(TaskCard.class));

        mockMvc.perform(delete("/taskcards/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());

        verify(taskCardService).deleteTaskCard(any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void deleteTaskCardShouldReturnStatusNoContent() throws Exception {
        mockMvc.perform(delete("/taskcards/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void whenDeleteTaskCardGetsWrongIdThenDenyDeleteAndReturnStatusNotFound() throws Exception {
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(delete("/taskcards/0").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());

        verify(taskCardService, never()).deleteTaskCard(any(TaskCard.class));
    }
}