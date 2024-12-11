package com.example.BuzzByte.controller;


import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.likes.PostLike;
import com.example.BuzzByte.service.PostLikeService;
import com.example.BuzzByte.service.PostService;
import com.example.BuzzByte.service.UserService;
import com.example.BuzzByte.utils.Result;
import com.example.BuzzByte.utils.converter.PostDtoConverter;
import com.example.BuzzByte.utils.converter.PostLikeDtoConverter;
import com.example.BuzzByte.utils.dto.PostLikeDto;
import com.example.BuzzByte.utils.dto.requests.AddPostLikeDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@SecurityRequirement(name = "bearerAuth")
public class PostLikeController {
    private final PostLikeService postLikeService;
    private final PostService postService;
    private final UserService userService;
    private final PostLikeDtoConverter postLikeDtoConverter;

    @GetMapping("/{likeId}")
    public Result<PostLikeDto> getLike(@PathVariable Long likeId) {
        PostLike like = postLikeService.getLike(likeId);
        PostLikeDto likeDto = postLikeDtoConverter.createFromEntity(like);
        return new Result<>(true, HttpStatus.OK.value(), "Like retrieved successfully", likeDto);
    }

    @GetMapping
    public Result<List<PostLikeDto>> getAllLikes() {
        List<PostLike> likes = postLikeService.getAllLikes();
        List<PostLikeDto> likeDtos = likes.stream()
                .map(postLikeDtoConverter::createFromEntity)
                .collect(Collectors.toList());
        return new Result<>(true, HttpStatus.OK.value(), "Likes retrieved successfully", likeDtos);
    }

    @GetMapping("/post/{postId}")
    public Result<List<PostLikeDto>> getLikesByPost(@PathVariable Long postId) {
        List<PostLike> likes = postLikeService.getLikesByPost(postId);
        List<PostLikeDto> likeDtos = likes.stream()
                .map(postLikeDtoConverter::createFromEntity)
                .collect(Collectors.toList());
        return new Result<>(true, HttpStatus.OK.value(), "Likes retrieved successfully", likeDtos);
    }

    @PostMapping
    public Result<PostLikeDto> addLike(@RequestBody AddPostLikeDto likeDto) {
        Post post = postService.getPost(likeDto.postId());
        PostLike like = postLikeDtoConverter.createFromAddPostLikeDtoWithPost(likeDto, post);
        var user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        like.setUserId(user.getId());
        PostLike addedLike = postLikeService.addLike(like);
        PostLikeDto addedLikeDto = postLikeDtoConverter.createFromEntity(addedLike);
        return new Result<>(true, HttpStatus.CREATED.value(), "Like added successfully", addedLikeDto);
    }

    @DeleteMapping("/{likeId}")
    public Result<String> deleteLike(@PathVariable Long likeId) {
        postLikeService.deleteLike(likeId);
        return new Result<>(true, HttpStatus.OK.value(), "Like deleted successfully", null);
    }


}
