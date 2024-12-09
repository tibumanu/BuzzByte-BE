package com.example.BuzzByte.controller;

import com.example.BuzzByte.controller.utils.HelperMethods;
import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.model.requests.GetPostsRequest;
import com.example.BuzzByte.model.responses.GetPostsResponse;
import com.example.BuzzByte.service.UserService;
import com.example.BuzzByte.utils.Result;
import com.example.BuzzByte.utils.converter.PostDtoConverter;
import com.example.BuzzByte.utils.dto.PostDto;
import com.example.BuzzByte.utils.dto.requests.AddPostDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.BuzzByte.service.PostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@SecurityRequirement(name = "bearerAuth")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    // converters
    private final PostDtoConverter postDtoConverter;


    @GetMapping("/{postId}")
    public Result<Post> getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved post based on given id", post);
    }

    //TODO: authorization
    @GetMapping
    public Result<Map<String, Object>> getPosts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "8") int pageSize,
            @RequestParam(required = false) Long postId,
            @RequestParam(required = false) String postTitle,
            @RequestParam(required = false) String postContent,
            @RequestParam(required = false) String postAuthor,
            @RequestParam(required = false) List<String> postTags,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> posts = postService.findAllByCriteria(postId, postTitle, postContent, postAuthor, postTags, startDate, endDate, pageable);
        Map<String, Object> response = HelperMethods.makeResponse(posts, postDtoConverter);
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved all posts based on given params", response);
    }

    // add post
    @PostMapping
    public Result<PostDto> addPost(@RequestBody AddPostDto addPostDto) {
        // get user from SecurityContextHolder
        var user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        // since the authentication is done only in FE we should hard code a user that does the adding of posts
        //var user = userService.getUserById(100);
        Post post = postDtoConverter.createFromAddPostDto(addPostDto);
        post.setUser(user);
        PostDto postDto = postDtoConverter.createFromEntity(postService.addPost(post));
        return new Result<>(true, HttpStatus.OK.value(), "Post added successfully", postDto);
    }

    @PostMapping("/demo")
    public Result<PostDto> addPost_Demo(@RequestBody AddPostDto addPostDto) {
        // get user from SecurityContextHolder
        //var user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        // since the authentication is done only in FE we should hard code a user that does the adding of posts
        var user = userService.getUserById(1);
        Post post = postDtoConverter.createFromAddPostDto(addPostDto);
        post.setUser(user);
        PostDto postDto = postDtoConverter.createFromEntity(postService.addPost(post));
        return new Result<>(true, HttpStatus.OK.value(), "Post added successfully", postDto);
    }

    //delete post
    @DeleteMapping("/{postId}")
    public Result<Post> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new Result<>(true, HttpStatus.OK.value(), "Post deleted successfully", null);
    }

    //update post
    @PutMapping("/{postId}")
    public Result<PostDto> updatePost(@PathVariable Long postId, @RequestBody AddPostDto addPostDto) {
        var user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Post post = postDtoConverter.createFromAddPostDto(addPostDto);
        post.setUser(user);
        post.setId(postId);
        PostDto postDto = postDtoConverter.createFromEntity(postService.updatePost(post));
        return new Result<>(true, HttpStatus.OK.value(), "Post updated successfully", postDto);
    }

    //update post
    @PutMapping("/demo/{postId}")
    public Result<PostDto> updatePost_Demo(@PathVariable Long postId, @RequestBody AddPostDto addPostDto) {
        //var user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Post post = postDtoConverter.createFromAddPostDto(addPostDto);
        var user = userService.getUserById(1);
        post.setUser(user);
        post.setId(postId);
        PostDto postDto = postDtoConverter.createFromEntity(postService.updatePost(post));
        return new Result<>(true, HttpStatus.OK.value(), "Post updated successfully", postDto);
    }
}
