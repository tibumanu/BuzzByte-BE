package com.example.BuzzByte.controller;


import com.example.BuzzByte.login_system.utils.converter.UserDtoConverter;
import com.example.BuzzByte.login_system.utils.dto.UserDto;
import com.example.BuzzByte.service.PostService;
import com.example.BuzzByte.service.UserService;
import com.example.BuzzByte.utils.Result;
import com.example.BuzzByte.utils.converter.PostDtoConverter;
import com.example.BuzzByte.utils.dto.PostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bookmarks")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Bookmark Management")
public class BookmarkController {
    private final UserService userService;
    private final PostService postService;
    private final UserDtoConverter userDtoConverter;
    private final PostDtoConverter postDtoConverter;

    @Operation(
            summary = "Add bookmark to user",
            description = "Adds a bookmark to a user based on the userId and postId."
    )
    @PostMapping("/{userId}")
    public Result<UserDto> addBookmarkToUser(@PathVariable Long userId, @RequestBody Long postId) {
        var updatedUser = userService.addBookmarkToUser(userId, postId);
        UserDto updatedUserDto = userDtoConverter.createFromEntity(updatedUser);
        return new Result<>(true, HttpStatus.OK.value(), "Bookmark added successfully.", updatedUserDto);
    }

    @Operation(
            summary = "Remove bookmark from user",
            description = "Removes a bookmark from a user based on the userId and postId."
    )
    @DeleteMapping("/{userId}")
    public Result<UserDto> removeBookmarkFromUser(@PathVariable Long userId, @RequestBody Long postId) {
        var updatedUser = userService.removeBookmarkFromUser(userId, postId);
        UserDto updatedUserDto = userDtoConverter.createFromEntity(updatedUser);
        return new Result<>(true, HttpStatus.OK.value(), "Bookmark removed successfully.", updatedUserDto);
    }

    @Operation(
            summary = "Get all bookmarks IDs of user",
            description = "Retrieves all bookmarks as postIds of a user based on the userId."
    )
    @GetMapping("/{userId}/ids")
    public Result<List<Long>> getBookmarksIdsOfUser(@PathVariable Long userId) {
        List<Long> bookmarkIds = userService.getBookmarksIdsOfUser(userId);
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved all bookmarks of user.", bookmarkIds);
    }
    
    @Operation(
            summary = "Get all bookmarks of user",
            description = "Retrieves all bookmarks as postDtos of a user based on the userId."
    )
    @GetMapping("/{userId}/postDtos")
    public Result<List<PostDto>> getBookmarksPostsDtosOfUser(@PathVariable Long userId) {
        List<Long> bookmarksIds = userService.getBookmarksIdsOfUser(userId);
        List<PostDto> bookmarkPostDtos = bookmarksIds.stream()
                .map(postId -> postDtoConverter.createFromEntity(postService.getPost(postId)))
                .toList();
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved all bookmarks of user.", bookmarkPostDtos);
    }
}
