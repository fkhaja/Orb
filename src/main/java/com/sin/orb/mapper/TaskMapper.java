package com.sin.orb.mapper;

import com.sin.orb.domain.Task;
import com.sin.orb.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "id", target = "taskId")
    TaskDto toDto(Task task);

    @Mapping(source = "taskId", target = "id")
    Task toEntity(TaskDto dto);
}
