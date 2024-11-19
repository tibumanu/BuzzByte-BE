package com.example.BuzzByte.utils.dto;

import com.example.BuzzByte.login_system.utils.dto.UserDto;

public record CommentDto(
        Long id,
        String content,
        UserDto user,
        Long postId
) {
}
