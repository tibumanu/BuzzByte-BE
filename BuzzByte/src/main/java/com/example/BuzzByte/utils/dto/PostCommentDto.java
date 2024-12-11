package com.example.BuzzByte.utils.dto;

import com.example.BuzzByte.login_system.utils.dto.UserDto;

public record PostCommentDto(
        Long id,
        String content,
        //Long userId,
        UserDto user,
        Long postId,
        String createdAt
) {
}

