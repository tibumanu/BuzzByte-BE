package controller;

import model.requests.GetPostsRequest;
import model.responses.GetPostsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.PostService;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //TODO: authorization
    @GetMapping("/posts")
    public GetPostsResponse getPosts(@RequestBody GetPostsRequest getPostsRequest) {
        return postService.getPosts(getPostsRequest.getPage(), getPostsRequest.getLimit());
    }
}
