package com.sin.orb.mapper;

import com.sin.orb.domain.TaskCard;
import com.sin.orb.dto.TaskCardDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = TaskMapper.class)
public interface TaskCardMapper {

    TaskCardMapper INSTANCE = Mappers.getMapper(TaskCardMapper.class);

    @Mapping(source = "id", target = "cardId")
    @Mapping(source = "completedAtTerm", target = "completedAtTerm")
    TaskCardDto toDto(TaskCard card);

    @Mapping(source = "cardId", target = "id")
    @Mapping(source = "completedAtTerm", target = "completedAtTerm")
    TaskCard toEntity(TaskCardDto dto);
}
