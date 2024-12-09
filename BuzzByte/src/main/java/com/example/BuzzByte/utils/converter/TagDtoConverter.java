package com.example.BuzzByte.utils.converter;

import com.example.BuzzByte.model.Tag;
import com.example.BuzzByte.repository.TagRepository;
import com.example.BuzzByte.utils.dto.TagDto;
import com.example.BuzzByte.utils.dto.requests.AddTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class TagDtoConverter implements Converter<Tag, TagDto> {
    // private final TagRepository tagRepository;
    @Override
    public TagDto createFromEntity(Tag tag) {
        return new TagDto(
                tag.getId(),
                tag.getName()
        );
    }

    @Override
    public Tag createFromDto(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.id())
                .name(tagDto.name())
                .build();
    }

    public Tag createFromAddTagDto(AddTagDto addTagDto) {
        return Tag.builder()
                .name(addTagDto.name())
                .build();
    }
}
