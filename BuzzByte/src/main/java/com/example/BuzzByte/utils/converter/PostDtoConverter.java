package com.example.BuzzByte.utils.converter;

import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.Tag;
import com.example.BuzzByte.repository.TagRepository;
import com.example.BuzzByte.utils.dto.PostCommentDto;
import com.example.BuzzByte.utils.dto.PostDto;
import com.example.BuzzByte.utils.dto.requests.AddPostDto;
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
                .user(new UserDtoConverter().createFromDto(dto.userDto()))
                .tags(dto.tags().stream().map(new TagDtoConverter()::createFromDto).collect(Collectors.toList()))
                .comments(dto.comments().stream().map(new PostCommentDtoConverter()::createFromDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public PostDto createFromEntity(Post entity) {

        return new PostDto(entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getContent(),
                entity.getTags().stream().map(new TagDtoConverter()::createFromEntity).collect(Collectors.toList()),
                new UserDtoConverter().createFromEntity(entity.getUser()),
                entity.getImage(),
                entity.getComments().stream().map(new PostCommentDtoConverter()::createFromEntity).collect(Collectors.toList()),
                entity.getLikes(),
                entity.getCreatedAt());
    }

    public Post createFromAddPostDto(AddPostDto addPostDto) {
        // get tags from provided dto
        var tags = addPostDto.tags().stream()
                .map(tagName ->
                        tagRepository.findByName(tagName)
                                .orElseGet(() -> tagRepository.save(new Tag(tagName)))
                )
                .toList();

        return Post.builder()
                .title(addPostDto.title())
                .description(addPostDto.description())
                .content(addPostDto.content())
                .tags(tags)
                .image(addPostDto.image())
                .build();
    }
}
