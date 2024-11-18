package com.example.BuzzByte.utils.dto.requests;

import java.util.List;

public record AddPostDto(
        String title,
        String description,
        String content,
        List<String> tags,
        String image,
        Long userId
) {
}
