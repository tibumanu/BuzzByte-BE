package com.example.BuzzByte.controller;


import com.example.BuzzByte.controller.utils.HelperMethods;
import com.example.BuzzByte.model.Tag;
import com.example.BuzzByte.service.TagService;
import com.example.BuzzByte.service.UserService;
import com.example.BuzzByte.utils.Result;
import com.example.BuzzByte.utils.converter.TagDtoConverter;
import com.example.BuzzByte.utils.dto.TagDto;
import com.example.BuzzByte.utils.dto.requests.AddTagDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
@SecurityRequirement(name = "bearerAuth")
public class TagController {
    private final TagService tagService;
    private final UserService userService;
    private final TagDtoConverter tagDtoConverter;

    @GetMapping("/{tagId}")
    public Result<Tag> getTag(@PathVariable Long tagId) {
        Tag tag = tagService.getTag(tagId);
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved tag based on given id", tag);
    }

    @GetMapping
    public Result<Map<String, Object>> getTags(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue =  "8") int pageSize,
            @RequestParam(required = false) Long tagId,
            @RequestParam(required = false) String tagName
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Tag> tags = tagService.findAllByCriteria(tagId, tagName, pageable);
        Map<String, Object> response = HelperMethods.makeResponse(tags, tagDtoConverter);
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved all tags based on given params", response);
    }

    @PostMapping
    public Result<TagDto> addTag(@RequestBody AddTagDto addTagDto) {
        var user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Tag tag = tagDtoConverter.createFromAddTagDto(addTagDto);
        TagDto tagDto = tagDtoConverter.createFromEntity(tagService.addTag(tag));
        return new Result<>(true, HttpStatus.OK.value(), "Tag added successfully", tagDto);
    }

    @DeleteMapping("/{tagId}")
    public Result<Tag> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return new Result<>(true, HttpStatus.OK.value(), "Tag deleted successfully", null);
    }

    @PutMapping("/{tagId}")
    public Result<TagDto> updateTag(@PathVariable Long tagId, @RequestBody AddTagDto addTagDto) {
        var user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Tag tag = tagDtoConverter.createFromAddTagDto(addTagDto);
        tag.setId(tagId);
        TagDto tagDto = tagDtoConverter.createFromEntity(tagService.updateTag(tag));
        return new Result<>(true, HttpStatus.OK.value(), "Tag updated successfully", tagDto);
    }
}
