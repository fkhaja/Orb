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
        entity.setName("test");
        entity.setId(1L);
        entity.setCreationDate(LocalDate.now());
        entity.setTasks(Collections.emptyList());
        entity.setDescription("description");
        entity.setImageUrl("url");
        entity.setDone(true);
        entity.setTerm(LocalDateTime.now());

        TaskCardDto dto = TaskCardMapper.INSTANCE.toDto(entity);
        assertThat(dto.getCardId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getCreationDate()).isEqualTo(entity.getCreationDate());
        assertThat(dto.getTasks()).isEqualTo(entity.getTasks());
        assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
        assertThat(dto.getImageUrl()).isEqualTo(entity.getImageUrl());
        assertThat(dto.getDone()).isEqualTo(entity.getDone());
        assertThat(dto.getTerm()).isEqualTo(entity.getTerm());
    }

    @Test
    void whenConvertTaskCardDtoToEntityThenCorrect() {
        TaskCardDto dto = new TaskCardDto();
        dto.setName("test");
        dto.setCardId(1L);
        dto.setCreationDate(LocalDate.now());
        dto.setTasks(Collections.emptyList());
        dto.setDescription("description");
        dto.setImageUrl("url");
        dto.setDone(true);
        dto.setTerm(LocalDateTime.now());

        TaskCard entity = TaskCardMapper.INSTANCE.toEntity(dto);
        assertThat(dto.getCardId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getCreationDate()).isEqualTo(entity.getCreationDate());
        assertThat(dto.getTasks()).isEqualTo(entity.getTasks());
        assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
        assertThat(dto.getImageUrl()).isEqualTo(entity.getImageUrl());
        assertThat(dto.getDone()).isEqualTo(entity.getDone());
        assertThat(dto.getTerm()).isEqualTo(entity.getTerm());
    }
}