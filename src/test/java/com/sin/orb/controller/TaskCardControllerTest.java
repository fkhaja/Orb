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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        MvcResult result = mockMvc.perform(get("/taskcards")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = result.getResponse().getStatus();
        List<TaskCardDto> resultList = Arrays.asList(
                mapper.readValue(result.getResponse().getContentAsString(), TaskCardDto[].class));

        verify(taskCardService).findAllForUser(eq(null));
        assertNotNull(resultList);
        assertEquals(HttpStatus.OK.value(), status, "Incorrect response status");
        assertEquals(2, resultList.size());
        assertEquals(1L, resultList.get(0).getCardId());
        assertEquals("test", resultList.get(0).getName());
        assertNotNull(resultList.get(0).getCreationDate());
        assertNotNull(resultList.get(0).getTasks());
        assertEquals(2L, resultList.get(1).getCardId());
        assertEquals("card", resultList.get(1).getName());
        assertNotNull(resultList.get(1).getCreationDate());
        assertNotNull(resultList.get(1).getTasks());
    }

    @Test
    @WithMockUser
    void getAllTaskCardsShouldReturnStatusOk() throws Exception {
        when(taskCardService.findAllForUser(eq(null))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/taskcards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getTaskCardShouldReturnCorrectDto() throws Exception {
        TaskCard cardStub = new TaskCard(1L, "test", LocalDate.now(), Collections.emptyList(), null);

        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenReturn(cardStub);

        MvcResult result = mockMvc.perform(get("/taskcards/1")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = result.getResponse().getStatus();
        TaskCardDto resultCard = mapper.readValue(result.getResponse().getContentAsString(), TaskCardDto.class);

        verify(taskCardService).findTaskCardForUser(any(Long.class), eq(null));
        assertNotNull(resultCard);
        assertEquals(HttpStatus.OK.value(), status, "Incorrect response status");
        assertEquals(1L, resultCard.getCardId());
        assertEquals("test", resultCard.getName());
        assertNotNull(resultCard.getCreationDate());
        assertNotNull(resultCard.getTasks());
    }

    @Test
    @WithMockUser
    void getTaskCardShouldReturnStatusOk() throws Exception {
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenReturn(mock(TaskCard.class));

        mockMvc.perform(get("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenGetTaskCardGetsWrongIdThenReturnStatusNotFound() throws Exception {
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/taskcards/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

        verify(taskCardService).findTaskCardForUser(any(Long.class), eq(null));
    }

    @Test
    @WithMockUser
    void createTaskCardShouldSaveCardAndReturnCorrectDto() throws Exception {
        TaskCard cardStub = new TaskCard(1L, "test", LocalDate.now(), Collections.emptyList(), null);
        when(taskCardService.saveTaskCard(any(TaskCard.class), eq(null))).thenReturn(cardStub);

        MvcResult result = mockMvc.perform(post("/taskcards")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .accept(MediaType.APPLICATION_JSON)
                                                   .content(mapper.writeValueAsString(cardStub))).andReturn();
        int status = result.getResponse().getStatus();
        TaskCardDto resultCard = mapper.readValue(result.getResponse().getContentAsString(), TaskCardDto.class);

        verify(taskCardService).saveTaskCard(any(TaskCard.class), eq(null));
        assertNotNull(resultCard);
        assertEquals(HttpStatus.CREATED.value(), status, "Incorrect response status");
        assertEquals(1L, resultCard.getCardId());
        assertEquals("test", resultCard.getName());
        assertNotNull(resultCard.getCreationDate());
        assertNotNull(resultCard.getTasks());
    }

    @Test
    @WithMockUser
    void createTaskCardShouldReturnStatusCreated() throws Exception {
        TaskCardDto body = new TaskCardDto();
        when(taskCardService.saveTaskCard(any(TaskCard.class), eq(null))).thenReturn(mock(TaskCard.class));

        mockMvc.perform(post("/taskcards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body))).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void createTaskCardWithEmptyBodyShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(post("/taskcards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
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

        MvcResult result = mockMvc.perform(put("/taskcards/1")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .accept(MediaType.APPLICATION_JSON)
                                                   .content(mapper.writeValueAsString(body))).andReturn();
        int status = result.getResponse().getStatus();
        TaskCardDto resultCard = mapper.readValue(result.getResponse().getContentAsString(), TaskCardDto.class);

        verify(taskCardService).findTaskCardForUser(any(Long.class), eq(null));
        verify(taskCardService).updateTaskCard(any(TaskCard.class), any(TaskCard.class));
        assertNotNull(resultCard);
        assertEquals(HttpStatus.OK.value(), status, "Incorrect response status");
        assertEquals(1L, resultCard.getCardId());
        assertEquals(body.getName(), resultCard.getName());
        assertNotNull(resultCard.getCreationDate());
        assertNotNull(resultCard.getTasks());
    }

    @Test
    @WithMockUser
    void updateTaskCardShouldReturnStatusOk() throws Exception {
        TaskCardDto body = new TaskCardDto();
        when(taskCardService.updateTaskCard(any(TaskCard.class), any(TaskCard.class))).thenReturn(mock(TaskCard.class));

        mockMvc.perform(put("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateTaskCardWithEmptyBodyShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(put("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void whenUpdateTaskCardGetsWrongIdThenReturnStatusNotFound() throws Exception {
        TaskCardDto body = new TaskCardDto();
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/taskcards/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body))).andExpect(status().isNotFound());

        verify(taskCardService).findTaskCardForUser(any(Long.class), eq(null));
        verify(taskCardService, never()).updateTaskCard(any(TaskCard.class), any(TaskCard.class));
    }

    @Test
    @WithMockUser
    void deleteTaskCardShouldDeleteCard() throws Exception {
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenReturn(mock(TaskCard.class));

        mockMvc.perform(delete("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        verify(taskCardService).deleteTaskCard(any(TaskCard.class));
        verify(taskCardService).findTaskCardForUser(any(Long.class), eq(null));
    }

    @Test
    @WithMockUser
    void deleteTaskCardShouldReturnStatusNoContent() throws Exception {
        mockMvc.perform(delete("/taskcards/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void whenDeleteTaskCardGetsWrongIdThenReturnStatusNotFound() throws Exception {
        when(taskCardService.findTaskCardForUser(any(Long.class), eq(null))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(delete("/taskcards/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

        verify(taskCardService).findTaskCardForUser(any(Long.class), eq(null));
        verify(taskCardService, never()).deleteTaskCard(any(TaskCard.class));
    }
}