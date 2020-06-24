package com.sin.orb.mapper;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.dto.TaskCardDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class TaskCardMapperTest {

    @Test
    void whenConvertTaskCardToDtoThenCorrect() {
        TaskCard entity = new TaskCard();
        entity.setTitle("test");
        entity.setId(1L);
        entity.setCreationDate(LocalDate.now());
        entity.setTasks(Collections.emptyList());
        entity.setDescription("description");
        entity.setImageUrl("url");
        entity.setDone(true);
        entity.setTerm(LocalDateTime.now());
        entity.setCompletedAtTerm(true);

        TaskCardDto dto = TaskCardMapper.INSTANCE.toDto(entity);
        assertThat(dto.getCardId()).isEqualTo(entity.getId());
        assertThat(dto.getTitle()).isEqualTo(entity.getTitle());
        assertThat(dto.getCreationDate()).isEqualTo(entity.getCreationDate());
        assertThat(dto.getTasks()).isEqualTo(entity.getTasks());
        assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
        assertThat(dto.getImageUrl()).isEqualTo(entity.getImageUrl());
        assertThat(dto.isDone()).isEqualTo(entity.isDone());
        assertThat(dto.getTerm()).isEqualTo(entity.getTerm());
        assertThat(dto.isCompletedAtTerm()).isEqualTo(entity.isCompletedAtTerm());
    }

    @Test
    void whenConvertTaskCardDtoToEntityThenCorrect() {
        TaskCardDto dto = new TaskCardDto();
        dto.setTitle("test");
        dto.setCardId(1L);
        dto.setCreationDate(LocalDate.now());
        dto.setTasks(Collections.emptyList());
        dto.setDescription("description");
        dto.setImageUrl("url");
        dto.setDone(true);
        dto.setTerm(LocalDateTime.now());
        dto.setCompletedAtTerm(true);

        TaskCard entity = TaskCardMapper.INSTANCE.toEntity(dto);
        assertThat(dto.getCardId()).isEqualTo(entity.getId());
        assertThat(dto.getTitle()).isEqualTo(entity.getTitle());
        assertThat(dto.getCreationDate()).isEqualTo(entity.getCreationDate());
        assertThat(dto.getTasks()).isEqualTo(entity.getTasks());
        assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
        assertThat(dto.getImageUrl()).isEqualTo(entity.getImageUrl());
        assertThat(dto.isDone()).isEqualTo(entity.isDone());
        assertThat(dto.getTerm()).isEqualTo(entity.getTerm());
        assertThat(dto.isCompletedAtTerm()).isEqualTo(entity.isCompletedAtTerm());
    }
}