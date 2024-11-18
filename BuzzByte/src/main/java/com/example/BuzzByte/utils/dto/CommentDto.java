package com.example.BuzzByte.utils.dto;

public record CommentDto(
        Long id,
        String content,
        UserDto user,
        Long postId
) {
}
