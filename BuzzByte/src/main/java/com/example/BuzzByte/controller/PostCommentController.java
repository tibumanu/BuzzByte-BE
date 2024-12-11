package com.example.BuzzByte.controller;

import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.comments.PostComment;
import com.example.BuzzByte.service.PostCommentService;
import com.example.BuzzByte.service.PostService;
import com.example.BuzzByte.service.UserService;
import com.example.BuzzByte.utils.Result;
import com.example.BuzzByte.utils.converter.PostCommentDtoConverter;
import com.example.BuzzByte.utils.dto.PostCommentDto;
import com.example.BuzzByte.utils.dto.requests.AddPostCommentDto;
import com.example.BuzzByte.utils.dto.requests.UpdatePostCommentDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@SecurityRequirement(name = "bearerAuth")
public class PostCommentController {

    private final PostCommentService postCommentService;
    private final PostService postService;
    private final UserService userService;
    private final PostCommentDtoConverter postCommentDtoConverter;


    @GetMapping("/{commentId}")
    public Result<PostCommentDto> getComment(@PathVariable Long commentId) {
        PostComment comment = postCommentService.getComment(commentId);
        PostCommentDto commentDto = postCommentDtoConverter.createFromEntity(comment);
        return new Result<>(true, HttpStatus.OK.value(), "Comment retrieved successfully", commentDto);
    }
    @GetMapping
    public Result<List<PostCommentDto>> getAllComments() {
        List<PostComment> comments = postCommentService.getAllComments();
        List<PostCommentDto> commentDtos = comments.stream()
                .map(postCommentDtoConverter::createFromEntity)
                .collect(Collectors.toList());
        return new Result<>(true, HttpStatus.OK.value(), "Comments retrieved successfully", commentDtos);
    }


    @GetMapping("/post/{postId}")
    public Result<List<PostCommentDto>> getCommentsByPost(@PathVariable Long postId) {
        List<PostComment> comments = postCommentService.getCommentsByPost(postId);
        List<PostCommentDto> commentDtos = comments.stream()
                .map(postCommentDtoConverter::createFromEntity)
                .collect(Collectors.toList());
        return new Result<>(true, HttpStatus.OK.value(), "Comments retrieved successfully", commentDtos);
    }

    @PostMapping
    public Result<PostCommentDto> addComment(@RequestBody AddPostCommentDto commentDto) {
        Post post = postService.getPost(commentDto.postId());
        PostComment comment = postCommentDtoConverter.createFromAddPostCommentDtoWithPost(commentDto, post);
        var user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setUserId(user.getId());
        PostComment savedComment = postCommentService.addComment(comment);
        PostCommentDto savedCommentDto = postCommentDtoConverter.createFromEntity(savedComment);
        return new Result<>(true, HttpStatus.CREATED.value(), "Comment added successfully", savedCommentDto);
    }

    @PutMapping("/{commentId}")
    public Result<PostCommentDto> updateComment(@PathVariable Long commentId, @RequestBody UpdatePostCommentDto commentDto) {
        PostComment newComment = postCommentDtoConverter.createFromUpdatePostCommentDto(commentDto);
        newComment.setId(commentId);
        var user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        PostComment savedComment = postCommentService.updateCommentIfAuthorized(newComment, user.getId());
        PostCommentDto savedCommentDto = postCommentDtoConverter.createFromEntity(savedComment);
        return new Result<>(true, HttpStatus.OK.value(), "Comment updated successfully", savedCommentDto);
    }

    @DeleteMapping("/{commentId}")
    public Result<Void> deleteComment(@PathVariable Long commentId) {
        var user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        postCommentService.deleteCommentIfAuthorized(commentId, user.getId());
        return new Result<>(true, HttpStatus.NO_CONTENT.value(), "Comment deleted successfully", null);
    }
}

