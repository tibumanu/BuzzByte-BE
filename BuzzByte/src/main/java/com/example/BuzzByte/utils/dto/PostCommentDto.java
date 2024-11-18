package com.example.BuzzByte.utils.dto;

public record PostCommentDto(
        Long id,
        String content,
        Long userId,
        Long postId,
        String createdAt
) {
}
