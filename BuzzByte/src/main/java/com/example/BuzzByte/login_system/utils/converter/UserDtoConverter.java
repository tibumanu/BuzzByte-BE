package com.example.BuzzByte.login_system.utils.converter;

import com.example.BuzzByte.login_system.utils.dto.ModifyUserDto;
import com.example.BuzzByte.login_system.utils.dto.UserDto;
import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.repository.TagRepository;
import com.example.BuzzByte.utils.converter.Converter;
import com.example.BuzzByte.utils.converter.TagDtoConverter;
import com.example.BuzzByte.utils.dto.requests.AddPostDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDtoConverter implements Converter<User, UserDto> {
    private final TagRepository tagRepository;
    @Override
    public User createFromDto(UserDto dto) {

        return User.builder()
                .id(dto.id())
                .username(dto.username())
                .email(dto.email())
                .role(dto.role())
                .profilePicture(dto.profilePicture())
                .tags(dto.tags().stream().map(new TagDtoConverter()::createFromDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public UserDto createFromEntity(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRole(),
                entity.getProfilePicture(),
                entity.getTags().stream().map(new TagDtoConverter()::createFromEntity).collect(Collectors.toList())
        );
    }

    public User createFromModifyUserDto(ModifyUserDto modifyUserDto) {
        var tags = modifyUserDto.tags().stream()
                .map(tagName ->
                        tagRepository.findByName(tagName)
                                .orElseThrow(() -> new EntityNotFoundException(String.format("Tag with name %s not found.", tagName)))
                )
                .toList();

        return User.builder()
                .username(modifyUserDto.username())
                .email(modifyUserDto.email())
                .tags(tags)
                .profilePicture(modifyUserDto.profilePicture())
                .build();
    }
}
