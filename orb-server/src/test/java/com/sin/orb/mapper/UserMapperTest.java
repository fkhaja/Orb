package com.sin.orb.mapper;

import com.sin.orb.domain.User;
import com.sin.orb.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void whenConvertUserToDtoThenCorrect() {
        User entity = new User();
        entity.setUsername("test");
        entity.setEmail("test@mail.com");
        entity.setImageUrl("url");

        UserDto dto = UserMapper.INSTANCE.toDto(entity);
        assertThat(dto.getUsername()).isSameAs(entity.getUsername());
        assertThat(dto.getEmail()).isSameAs(entity.getEmail());
        assertThat(dto.getImageUrl()).isSameAs(entity.getImageUrl());
    }
}