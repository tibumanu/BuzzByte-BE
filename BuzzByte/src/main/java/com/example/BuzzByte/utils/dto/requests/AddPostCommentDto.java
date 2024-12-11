package com.example.BuzzByte.utils.dto.requests;

import com.example.BuzzByte.login_system.utils.dto.UserDto;

public record AddPostCommentDto(
        String content,
        Long postId
) {
}
