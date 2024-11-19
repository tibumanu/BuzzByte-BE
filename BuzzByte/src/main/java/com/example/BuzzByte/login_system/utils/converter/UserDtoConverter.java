package com.example.BuzzByte.login_system.utils.converter;

import com.example.BuzzByte.login_system.utils.dto.UserDto;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.utils.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements Converter<User, UserDto> {
    @Override
    public User createFromDto(UserDto dto) {
        return User.builder()
                .id(dto.id())
                .username(dto.username())
                .email(dto.email())
                .role(dto.role())
                .build();
    }

    @Override
    public UserDto createFromEntity(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRole());
    }
}
