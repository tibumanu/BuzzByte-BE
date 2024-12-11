package com.example.BuzzByte.utils.dto;

import com.example.BuzzByte.login_system.utils.dto.UserDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public record PostDto (
        Long id,
        String title,
        String content,
        List<TagDto> tags,
        UserDto userDto,
//        String image,  // todo: should be deleted?
        byte[] byteImage,  // todo: should be renamed to just 'image' after we get rid of the previous 'String image' field
        List<PostCommentDto> comments,
        Long likes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}