package com.example.BuzzByte.utils.dto;

import com.example.BuzzByte.login_system.utils.dto.UserDto;

import java.time.Instant;
import java.util.List;

public record PostDto (
        Long id,
        String title,
        String description,
        String content,
        List<TagDto> tags,
        UserDto userDto,
        String image,
        List<PostCommentDto> comments,
        Long likes,
        Instant createdAt
) {
}