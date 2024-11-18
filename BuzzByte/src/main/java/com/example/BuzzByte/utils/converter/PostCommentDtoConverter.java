package com.example.BuzzByte.utils.converter;

import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.comments.Comment;
import com.example.BuzzByte.model.comments.PostComment;
import com.example.BuzzByte.utils.dto.CommentDto;
import com.example.BuzzByte.utils.dto.PostCommentDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;

@Component

public class PostCommentDtoConverter implements Converter<PostComment, PostCommentDto> {


    public PostComment createFromDtoWithPost(PostCommentDto dto, Post post) {
        return PostComment.builder()
                .id(dto.id()).content(dto.content())
                .createdAt(dto.createdAt().transform(Instant::parse))
                .userId(dto.userId())
                .post(post).build();
    }

    @Override
    public PostComment createFromDto(PostCommentDto dto) {
        // makes Post field be null
        return PostComment.builder()
                .id(dto.id()).content(dto.content())
                .createdAt(dto.createdAt().transform(Instant::parse))
                .userId(dto.userId()).post(null).build();
    }

    @Override
    public PostCommentDto createFromEntity(PostComment entity) {
        return new PostCommentDto(entity.getId(),
                entity.getContent(),
                entity.getUserId(),
                entity.getPost().getId(),
                entity.getCreatedAt().toString());
    }
}
