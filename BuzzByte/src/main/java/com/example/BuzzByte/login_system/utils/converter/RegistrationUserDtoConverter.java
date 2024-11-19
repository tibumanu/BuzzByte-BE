package com.example.BuzzByte.login_system.utils.converter;


import com.example.BuzzByte.login_system.utils.dto.RegistrationUserDto;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.utils.converter.Converter;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
public class RegistrationUserDtoConverter implements Converter<User, RegistrationUserDto> {

    @Override
    public User createFromDto(RegistrationUserDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .hashedPassword(dto.getPassword())
                .email(dto.getEmail())
                .role(dto.getRole())
                .uniqueKey(UUID.randomUUID())
                .build();

    }

    @Override
    public RegistrationUserDto createFromEntity(User entity) {
        return null;
    }
}
