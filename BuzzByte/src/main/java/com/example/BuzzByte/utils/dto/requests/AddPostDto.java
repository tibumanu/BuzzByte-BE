package com.example.BuzzByte.utils.dto.requests;

import java.util.List;

public record AddPostDto(
        String title,
        String content,
        List<String> tags,
//        String image  // todo: should be deleted?
        byte[] byteImage  // todo: should be renamed to just 'image' after we get rid of the previous 'String image' field
        // , Long userId // should get user from the SecurityContextHolder
) {
}
