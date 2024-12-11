package com.example.BuzzByte.utils.converter;

import com.example.BuzzByte.login_system.utils.converter.UserDtoConverter;
import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.Tag;
import com.example.BuzzByte.repository.TagRepository;
import com.example.BuzzByte.utils.dto.PostCommentDto;
import com.example.BuzzByte.utils.dto.PostDto;
import com.example.BuzzByte.utils.dto.requests.AddPostDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostDtoConverter implements Converter<Post, PostDto>{

     private final TagRepository tagRepository;

    @Override
    public Post createFromDto(PostDto dto) {
        return Post.builder()
                .id(dto.id())
                .content(dto.content())
                .user(new UserDtoConverter(tagRepository).createFromDto(dto.userDto()))
                .tags(dto.tags().stream().map(new TagDtoConverter()::createFromDto).collect(Collectors.toList()))
                .comments(dto.comments().stream().map(new PostCommentDtoConverter()::createFromDto).collect(Collectors.toList()))
                .byteImage(dto.byteImage())
                .build();
    }

    @Override
    public PostDto createFromEntity(Post entity) {

        return new PostDto(entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getTags().stream().map(new TagDtoConverter()::createFromEntity).collect(Collectors.toList()),
                new UserDtoConverter(tagRepository).createFromEntity(entity.getUser()),
                entity.getByteImage(),
                entity.getComments() == null || entity.getComments().isEmpty()
                        ? null
                        : entity.getComments().stream()
                        .map(new PostCommentDtoConverter()::createFromEntity)
                        .collect(Collectors.toList()),
                entity.getLikes() == null ? 0 : entity.getLikes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public Post createFromAddPostDto(AddPostDto addPostDto) {
        // get tags from provided dto
        var tags = addPostDto.tags().stream()
                .map(tagName ->
                        tagRepository.findByName(tagName)
                                .orElseThrow(() -> new EntityNotFoundException(String.format("Tag with name %s not found.", tagName)))
                )
                .toList();

        return Post.builder()
                .title(addPostDto.title())
                .content(addPostDto.content())
                .tags(tags)
                .likes(0L)
                .byteImage(addPostDto.byteImage())
                .build();
    }
}
