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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.BuzzByte.service.PostService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    // converters
    private final PostDtoConverter postDtoConverter;

    //TODO: authorization
    @GetMapping
    public Result<Map<String, Object>> getPosts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "8") int pageSize,
            @RequestParam(required = false) Long postId,
            @RequestParam(required = false) String postTitle,
            @RequestParam(required = false) String postContent,
            @RequestParam(required = false) String postAuthor,
            @RequestParam(required = false) String postCategory
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> posts = postService.findAllByCriteria(postId, postTitle, postContent, postAuthor, postCategory, pageable);
        Map<String, Object> response = HelperMethods.makeResponse(posts, null);
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved all posts based on given params", response);
    }

    // add post
    @PostMapping
    public Result<Post> addPost(@RequestBody AddPostDto addPostDto) {
        // get user from the id provided in the request
        User user = userService.getUserById(addPostDto.userId());
        Post post = postDtoConverter.createFromAddPostDto(addPostDto);
        post.setUser(user);
        postService.addPost(post);
        return new Result<>(true, HttpStatus.OK.value(), "Post added successfully", post);
    }
}
