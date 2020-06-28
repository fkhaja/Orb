package com.sin.orb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sin.orb.domain.TaskCard;
import com.sin.orb.dto.TaskCardDto;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.service.TaskCardService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private static List<TaskCard> cardStubs;

    private static TaskCardDto defaultBody;

    @BeforeAll
    public static void setUp() {
        TaskCard firstStub = new TaskCard();
        firstStub.setId(1L);
        firstStub.setTitle("title_1");
        firstStub.setDescription("description_1");
        firstStub.setImageUrl("url_1");
        firstStub.setCompletedAtTerm(true);
        firstStub.setTasks(Collections.emptyList());
        firstStub.setDone(true);
        firstStub.setCreationDate(LocalDate.now());
        firstStub.setTerm(LocalDateTime.now());

        TaskCard secondStub = new TaskCard();
        secondStub.setId(2L);
        secondStub.setTitle("title_2");
        secondStub.setDescription("description_2");
        secondStub.setImageUrl("url_2");
        secondStub.setCompletedAtTerm(true);
        secondStub.setTasks(Collections.emptyList());
        secondStub.setDone(true);
        secondStub.setCreationDate(LocalDate.now());
        secondStub.setTerm(LocalDateTime.now());

        cardStubs = List.of(firstStub, secondStub);

        defaultBody = new TaskCardDto();
        defaultBody.setTitle("title");
        defaultBody.setDescription("description");
        defaultBody.setImageUrl("url");
        defaultBody.setCompletedAtTerm(true);
        defaultBody.setDone(true);
        defaultBody.setTerm(LocalDateTime.now());
    }

    @Test
    @WithMockUser
    void getAllTaskCardsShouldReturnCorrectDtoList() throws Exception {
        Page<TaskCard> page = new PageImpl<>(cardStubs);

        when(taskCardService.findAllForUser(any(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.content", hasSize(2)))
               .andExpect(jsonPath("$.content[0].cardId", equalTo(1)))
               .andExpect(jsonPath("$.content[0].title", equalTo("title_1")))
               .andExpect(jsonPath("$.content[0].creationDate", notNullValue()))
               .andExpect(jsonPath("$.content[0].tasks", hasSize(0)))
               .andExpect(jsonPath("$.content[0].description", equalTo("description_1")))
               .andExpect(jsonPath("$.content[0].term", notNullValue()))
               .andExpect(jsonPath("$.content[0].imageUrl", equalTo("url_1")))
               .andExpect(jsonPath("$.content[0].done", equalTo(true)))
               .andExpect(jsonPath("$.content[0].completedAtTerm", equalTo(true)))
               .andExpect(jsonPath("$.content[1].cardId", equalTo(2)))
               .andExpect(jsonPath("$.content[1].title", equalTo("title_2")))
               .andExpect(jsonPath("$.content[1].creationDate", notNullValue()))
               .andExpect(jsonPath("$.content[1].tasks", hasSize(0)))
               .andExpect(jsonPath("$.content[1].description", equalTo("description_2")))
               .andExpect(jsonPath("$.content[1].term", notNullValue()))
               .andExpect(jsonPath("$.content[1].imageUrl", equalTo("url_2")))
               .andExpect(jsonPath("$.content[1].done", equalTo(true)))
               .andExpect(jsonPath("$.content[1].completedAtTerm", equalTo(true)))
               .andExpect(jsonPath("$.totalElements", equalTo(2)))
               .andExpect(status().isOk());

        verify(taskCardService).findAllForUser(any(), any(Pageable.class));
    }

    @Test
    @WithMockUser
    void getAllTaskCardsShouldReturnStatusOk() throws Exception {
        when(taskCardService.findAllForUser(any(), any(Pageable.class))).thenReturn(Page.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getTaskCardShouldReturnCorrectDto() throws Exception {
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenReturn(cardStubs.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.cardId", equalTo(1)))
               .andExpect(jsonPath("$.title", equalTo("title_1")))
               .andExpect(jsonPath("$.creationDate", notNullValue()))
               .andExpect(jsonPath("$.tasks", hasSize(0)))
               .andExpect(jsonPath("$.description", equalTo("description_1")))
               .andExpect(jsonPath("$.term", notNullValue()))
               .andExpect(jsonPath("$.imageUrl", equalTo("url_1")))
               .andExpect(jsonPath("$.done", equalTo(true)))
               .andExpect(jsonPath("$.completedAtTerm", equalTo(true)))
               .andExpect(status().isOk());

        verify(taskCardService).findTaskCardForUser(anyLong(), any());
    }

    @Test
    @WithMockUser
    void getTaskCardShouldReturnStatusOk() throws Exception {
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenReturn(cardStubs.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenGetTaskCardGetsWrongIdThenReturnStatusNotFound() throws Exception {
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards/0").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());

        verify(taskCardService).findTaskCardForUser(anyLong(), any());
    }

    @Test
    @WithMockUser
    void createTaskCardShouldSaveCardAndReturnCorrectDto() throws Exception {
        when(taskCardService.saveTaskCard(any(TaskCard.class), any())).thenReturn(cardStubs.get(0));

        mockMvc.perform(post("/taskcards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.cardId", equalTo(1)))
               .andExpect(jsonPath("$.title", equalTo("title_1")))
               .andExpect(jsonPath("$.creationDate", notNullValue()))
               .andExpect(jsonPath("$.tasks", hasSize(0)))
               .andExpect(jsonPath("$.description", equalTo("description_1")))
               .andExpect(jsonPath("$.term", notNullValue()))
               .andExpect(jsonPath("$.imageUrl", equalTo("url_1")))
               .andExpect(jsonPath("$.done", equalTo(true)))
               .andExpect(jsonPath("$.completedAtTerm", equalTo(true)))
               .andExpect(status().isCreated());

        verify(taskCardService).saveTaskCard(any(TaskCard.class), any());
    }

    @Test
    @WithMockUser
    void createTaskCardShouldReturnStatusCreated() throws Exception {
        when(taskCardService.saveTaskCard(any(TaskCard.class), any())).thenReturn(cardStubs.get(0));

        mockMvc.perform(post("/taskcards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
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
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenReturn(cardStubs.get(0));
        when(taskCardService.updateTaskCard(any(TaskCard.class), any(TaskCard.class))).thenReturn(cardStubs.get(1));

        mockMvc.perform(put("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.cardId", equalTo(2)))
               .andExpect(jsonPath("$.title", equalTo("title_2")))
               .andExpect(jsonPath("$.creationDate", notNullValue()))
               .andExpect(jsonPath("$.tasks", hasSize(0)))
               .andExpect(jsonPath("$.description", equalTo("description_2")))
               .andExpect(jsonPath("$.term", notNullValue()))
               .andExpect(jsonPath("$.imageUrl", equalTo("url_2")))
               .andExpect(jsonPath("$.done", equalTo(true)))
               .andExpect(jsonPath("$.completedAtTerm", equalTo(true)))
               .andExpect(status().isOk());

        verify(taskCardService).updateTaskCard(any(TaskCard.class), any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void updateTaskCardShouldReturnStatusOk() throws Exception {
        when(taskCardService.updateTaskCard(any(TaskCard.class), any(TaskCard.class))).thenReturn(cardStubs.get(0));

        mockMvc.perform(put("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
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
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/taskcards/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(status().isNotFound());

        verify(taskCardService, never()).updateTaskCard(any(TaskCard.class), any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void deleteTaskCardShouldDeleteCard() throws Exception {
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenReturn(cardStubs.get(0));

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
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(delete("/taskcards/0").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());

        verify(taskCardService, never()).deleteTaskCard(any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void patchTaskCardShouldUpdatePassedFields() throws Exception {
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenReturn(cardStubs.get(0));
        when(taskCardService.partlyUpdateTaskCard(any(TaskCard.class), anyMap())).thenReturn(cardStubs.get(1));

        mockMvc.perform(patch("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.cardId", equalTo(2)))
               .andExpect(jsonPath("$.title", equalTo("title_2")))
               .andExpect(jsonPath("$.creationDate", notNullValue()))
               .andExpect(jsonPath("$.tasks", hasSize(0)))
               .andExpect(jsonPath("$.description", equalTo("description_2")))
               .andExpect(jsonPath("$.term", notNullValue()))
               .andExpect(jsonPath("$.imageUrl", equalTo("url_2")))
               .andExpect(jsonPath("$.done", equalTo(true)))
               .andExpect(jsonPath("$.completedAtTerm", equalTo(true)))
               .andExpect(status().isOk());

        verify(taskCardService).partlyUpdateTaskCard(any(TaskCard.class), anyMap());
    }

    @Test
    @WithMockUser
    void patchTaskCardShouldReturnStatusOk() throws Exception {
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenReturn(cardStubs.get(0));
        when(taskCardService.partlyUpdateTaskCard(any(TaskCard.class), anyMap())).thenReturn(cardStubs.get(0));

        mockMvc.perform(patch("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenPatchTaskCardGetsWrongIdThenDenyUpdateAndReturnStatusNotFound() throws Exception {
        when(taskCardService.findTaskCardForUser(anyLong(), any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(patch("/taskcards/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(defaultBody)))
               .andExpect(status().isNotFound());

        verify(taskCardService, never()).partlyUpdateTaskCard(any(TaskCard.class), anyMap());
    }
}