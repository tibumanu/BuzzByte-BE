package com.example.BuzzByte.login_system.utils.converter;

import com.example.BuzzByte.login_system.utils.dto.UserAdminDto;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.utils.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserAdminDtoConverter implements Converter<User, UserAdminDto> {
    @Override
    public User createFromDto(UserAdminDto dto) {
        return User.builder()
                .id(dto.id())
                .username(dto.username())
                .email(dto.email())
                .isEnabled(dto.isEnabled())
                .role(dto.role())
                .profilePicture(dto.profilePicture())
                .build();
    }

    @Override
    public UserAdminDto createFromEntity(User entity) {
        return new UserAdminDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRole(),
                entity.getProfilePicture(),
                entity.isEnabled());
    }
}
