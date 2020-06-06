package com.sin.orb.mapper;

import com.sin.orb.domain.User;
import com.sin.orb.dto.UserDto;
import com.sin.orb.security.AuthProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void whenConvertUserToDtoThenCorrect() {
        User entity = new User();
        entity.setUsername("test");
        entity.setEmail("test@mail.com");
        entity.setImageUrl("url");
        entity.setProvider(AuthProvider.LOCAL);
        entity.setProviderId(null);

        UserDto dto = UserMapper.INSTANCE.toDto(entity);
        assertThat(dto.getUsername()).isSameAs(entity.getUsername());
        assertThat(dto.getEmail()).isSameAs(entity.getEmail());
        assertThat(dto.getImageUrl()).isSameAs(entity.getImageUrl());
    }
}