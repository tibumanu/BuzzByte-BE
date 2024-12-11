package com.example.BuzzByte.utils.dto.requests;

import java.util.List;

public record AddPostDto(
        String title,
        String content,
        List<String> tags,
        byte[] image
        // , Long userId // should get user from the SecurityContextHolder
) {
}
