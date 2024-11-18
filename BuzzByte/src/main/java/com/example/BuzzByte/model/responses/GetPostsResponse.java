package com.example.BuzzByte.model.responses;

import com.example.BuzzByte.model.Post;
import org.springframework.data.domain.Page;

public class GetPostsResponse {
    private Page<Post> pagePost;

    public GetPostsResponse(Page<Post> pagePost) {
        this.pagePost = pagePost;
    }

    public Page<Post> getPagePost() {
        return pagePost;
    }

    public void setPagePost(Page<Post> pagePost) {
        this.pagePost = pagePost;
    }
}
