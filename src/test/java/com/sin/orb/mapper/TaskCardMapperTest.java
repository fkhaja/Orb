package com.sin.orb.mapper;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.dto.TaskCardDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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

        TaskCardDto dto = TaskCardMapper.INSTANCE.toDto(entity);
        assertThat(dto.getCardId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getCreationDate()).isEqualTo(entity.getCreationDate());
        assertThat(dto.getTasks()).isEqualTo(entity.getTasks());
    }

    @Test
    void whenConvertTaskCardDtoToEntityThenCorrect() {
        TaskCardDto dto = new TaskCardDto();
        dto.setName("test");
        dto.setCardId(1L);
        dto.setCreationDate(LocalDate.now());
        dto.setTasks(Collections.emptyList());

        TaskCard entity = TaskCardMapper.INSTANCE.toEntity(dto);
        assertThat(dto.getCardId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getCreationDate()).isEqualTo(entity.getCreationDate());
        assertThat(dto.getTasks()).isEqualTo(entity.getTasks());
    }
}