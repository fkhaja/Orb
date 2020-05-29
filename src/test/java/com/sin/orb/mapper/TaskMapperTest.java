package com.sin.orb.mapper;

import com.sin.orb.domain.Task;
import com.sin.orb.dto.TaskDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    @Test
    void whenConvertTaskToDtoThenCorrect() {
        Task entity = new Task();
        entity.setValue("test");
        entity.setId(1L);
        entity.setCompleted(true);

        TaskDto dto = TaskMapper.INSTANCE.toDto(entity);
        assertThat(dto.getValue()).isSameAs(entity.getValue());
        assertThat(dto.getTaskId()).isEqualTo(entity.getId());
        assertThat(dto.getCompleted()).isEqualTo(entity.getCompleted());
    }

    @Test
    void whenConvertTaskDtoToEntityThenCorrect() {
        TaskDto dto = new TaskDto();
        dto.setValue("test");
        dto.setTaskId(1L);
        dto.setCompleted(true);

        Task entity = TaskMapper.INSTANCE.toEntity(dto);
        assertThat(entity.getValue()).isSameAs(dto.getValue());
        assertThat(entity.getId()).isEqualTo(dto.getTaskId());
        assertThat(entity.getCompleted()).isEqualTo(dto.getCompleted());
    }
}