package com.example.BuzzByte.utils.dto;

import com.example.BuzzByte.login_system.utils.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public record PostDto (
        Long id,
        String title,
        String content,
        List<TagDto> tags,
        UserDto userDto,
        byte[] image,
        List<PostCommentDto> comments,
        Long likes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}