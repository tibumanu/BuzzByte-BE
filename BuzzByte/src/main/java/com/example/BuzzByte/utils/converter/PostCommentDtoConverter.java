package com.example.BuzzByte.utils.converter;

import com.example.BuzzByte.login_system.utils.converter.UserDtoConverter;
import com.example.BuzzByte.login_system.utils.dto.UserDto;
import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.model.comments.Comment;
import com.example.BuzzByte.model.comments.PostComment;
import com.example.BuzzByte.service.UserService;
import com.example.BuzzByte.utils.dto.CommentDto;
import com.example.BuzzByte.utils.dto.PostCommentDto;
import com.example.BuzzByte.utils.dto.requests.AddPostCommentDto;
import com.example.BuzzByte.utils.dto.requests.UpdatePostCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostCommentDtoConverter implements Converter<PostComment, PostCommentDto> {
    private final UserService userService;
    private final UserDtoConverter userDtoConverter;

    public PostComment createFromAddPostCommentDtoWithPost(AddPostCommentDto dto, Post post) {
        return PostComment.builder()
                .content(dto.content())
                .post(post).build();
    }

    public PostComment createFromUpdatePostCommentDto(UpdatePostCommentDto dto) {
        return PostComment.builder()
                .content(dto.content())
                .build();
    }

    @Override
    public PostComment createFromDto(PostCommentDto dto) {
        // makes Post field be null
        return PostComment.builder()
                .id(dto.id()).content(dto.content())
                .createdAt(dto.createdAt() != null ? Instant.parse(dto.createdAt()) : null)
                .userId(dto.user().id()).post(null).build();
    }

    @Override
    public PostCommentDto createFromEntity(PostComment entity) {
        User user = userService.getUserById(entity.getUserId());
        UserDto userDto = userDtoConverter.createFromEntity(user);
        return new PostCommentDto(entity.getId(),
                entity.getContent(),
                userDto,
                entity.getPost().getId(),
                entity.getCreatedAt().toString());
    }
}
