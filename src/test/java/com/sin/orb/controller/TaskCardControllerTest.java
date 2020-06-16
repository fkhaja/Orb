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

    @Test
    @WithMockUser
    void getAllTaskCardsShouldReturnCorrectDtoList() throws Exception {
        TaskCard firstStub = new TaskCard();
        firstStub.setId(1L);
        firstStub.setName("test");
        firstStub.setCreationDate(LocalDate.now());
        firstStub.setTasks(Collections.emptyList());
        firstStub.setDescription("description1");
        firstStub.setTerm(LocalDateTime.MAX);
        firstStub.setImageUrl("url1");
        firstStub.setDone(true);

        TaskCard secondStub = new TaskCard();
        secondStub.setId(2L);
        secondStub.setName("card");
        secondStub.setCreationDate(LocalDate.now());
        secondStub.setTasks(Collections.emptyList());
        secondStub.setDescription("description2");
        secondStub.setTerm(LocalDateTime.MIN);
        secondStub.setImageUrl("url2");
        secondStub.setDone(true);

        Page<TaskCard> stubPage = new PageImpl<>(List.of(firstStub, secondStub));

        when(taskCardService.findAllForUser(eq(null), any(Pageable.class))).thenReturn(stubPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.content", hasSize(2)))
               .andExpect(jsonPath("$.content[0].cardId", equalTo(1)))
               .andExpect(jsonPath("$.content[0].name", equalTo("test")))
               .andExpect(jsonPath("$.content[0].creationDate", notNullValue()))
               .andExpect(jsonPath("$.content[0].tasks", hasSize(0)))
               .andExpect(jsonPath("$.content[0].description", equalTo("description1")))
               .andExpect(jsonPath("$.content[0].term", notNullValue()))
               .andExpect(jsonPath("$.content[0].imageUrl", equalTo("url1")))
               .andExpect(jsonPath("$.content[0].done", equalTo(true)))
               .andExpect(jsonPath("$.content[1].cardId", equalTo(2)))
               .andExpect(jsonPath("$.content[1].name", equalTo("card")))
               .andExpect(jsonPath("$.content[1].creationDate", notNullValue()))
               .andExpect(jsonPath("$.content[1].tasks", hasSize(0)))
               .andExpect(jsonPath("$.content[1].description", equalTo("description2")))
               .andExpect(jsonPath("$.content[1].term", notNullValue()))
               .andExpect(jsonPath("$.content[1].imageUrl", equalTo("url2")))
               .andExpect(jsonPath("$.content[1].done", equalTo(true)))
               .andExpect(status().isOk());

        verify(taskCardService).findAllForUser(eq(null), any(Pageable.class));
    }

    @Test
    @WithMockUser
    void getAllTaskCardsShouldReturnStatusOk() throws Exception {
        when(taskCardService.findAllForUser(eq(null), any(Pageable.class))).thenReturn(Page.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getTaskCardShouldReturnCorrectDto() throws Exception {
        TaskCard cardStub = new TaskCard();
        cardStub.setId(1L);
        cardStub.setName("test");
        cardStub.setCreationDate(LocalDate.now());
        cardStub.setTasks(Collections.emptyList());
        cardStub.setDescription("description");
        cardStub.setTerm(LocalDateTime.MAX);
        cardStub.setImageUrl("url");
        cardStub.setDone(true);

        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenReturn(cardStub);

        mockMvc.perform(MockMvcRequestBuilders.get("/taskcards/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.cardId", equalTo(1)))
               .andExpect(jsonPath("$.name", equalTo("test")))
               .andExpect(jsonPath("$.creationDate", notNullValue()))
               .andExpect(jsonPath("$.tasks", hasSize(0)))
               .andExpect(jsonPath("$.description", equalTo("description")))
               .andExpect(jsonPath("$.term", notNullValue()))
               .andExpect(jsonPath("$.imageUrl", equalTo("url")))
               .andExpect(jsonPath("$.done", equalTo(true)))
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
        TaskCard cardStub = new TaskCard();
        cardStub.setId(1L);
        cardStub.setName("test");
        cardStub.setCreationDate(LocalDate.now());
        cardStub.setTasks(Collections.emptyList());
        cardStub.setDescription("description");
        cardStub.setTerm(LocalDateTime.MAX);
        cardStub.setImageUrl("url");
        cardStub.setDone(true);

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
               .andExpect(jsonPath("$.description", equalTo("description")))
               .andExpect(jsonPath("$.term", notNullValue()))
               .andExpect(jsonPath("$.imageUrl", equalTo("url")))
               .andExpect(jsonPath("$.done", equalTo(true)))
               .andExpect(status().isCreated());

        verify(taskCardService).saveTaskCard(any(TaskCard.class), eq(null));
    }

    @Test
    @WithMockUser
    void createTaskCardShouldReturnStatusCreated() throws Exception {
        TaskCardDto body = new TaskCardDto();
        body.setName("test");

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
        TaskCard existingStub = new TaskCard();
        existingStub.setId(1L);
        existingStub.setName("test");
        existingStub.setCreationDate(LocalDate.now());
        existingStub.setTasks(Collections.emptyList());
        existingStub.setDescription("description");
        existingStub.setTerm(LocalDateTime.MAX);
        existingStub.setImageUrl("url");

        TaskCard replacementStub = new TaskCard();
        replacementStub.setId(1L);
        replacementStub.setName("card");
        replacementStub.setCreationDate(LocalDate.now());
        replacementStub.setTasks(Collections.emptyList());
        replacementStub.setDescription("description upd");
        replacementStub.setTerm(LocalDateTime.MIN);
        replacementStub.setImageUrl("url upd");
        replacementStub.setDone(true);

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
               .andExpect(jsonPath("$.description", equalTo("description upd")))
               .andExpect(jsonPath("$.term", notNullValue()))
               .andExpect(jsonPath("$.imageUrl", equalTo("url upd")))
               .andExpect(jsonPath("$.done", equalTo(true)))
               .andExpect(status().isOk());

        verify(taskCardService).updateTaskCard(any(TaskCard.class), any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void updateTaskCardShouldReturnStatusOk() throws Exception {
        TaskCardDto body = new TaskCardDto();
        body.setName("test");

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
        body.setName("test");

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

    @Test
    @WithMockUser
    void patchTaskCardShouldUpdatePassedFields() throws Exception {
        TaskCardDto body = new TaskCardDto();
        body.setName("test");

        TaskCard result = new TaskCard();
        result.setName("test");

        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenReturn(mock(TaskCard.class));
        when(taskCardService.partlyUpdateTaskCard(any(TaskCard.class), anyMap())).thenReturn(result);

        mockMvc.perform(patch("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.name", equalTo("test")));

        verify(taskCardService).partlyUpdateTaskCard(any(TaskCard.class), anyMap());
    }

    @Test
    @WithMockUser
    void patchTaskCardShouldReturnStatusOk() throws Exception {
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenReturn(mock(TaskCard.class));
        when(taskCardService.partlyUpdateTaskCard(any(TaskCard.class), anyMap())).thenReturn(mock(TaskCard.class));

        mockMvc.perform(patch("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(new TaskCardDto())))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenPatchTaskCardGetsWrongIdThenDenyUpdateAndReturnStatusNotFound() throws Exception {
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(patch("/taskcards/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(new TaskCardDto())))
               .andExpect(status().isNotFound());

        verify(taskCardService, never()).partlyUpdateTaskCard(any(TaskCard.class), anyMap());
    }
}