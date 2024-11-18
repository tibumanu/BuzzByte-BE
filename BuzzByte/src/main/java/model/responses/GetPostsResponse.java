package model.responses;

import model.Post;

import java.util.List;

public class GetPostsResponse {
    private List<Post> posts;
    private int nextOffset;

    public GetPostsResponse(List<Post> posts, int nextOffset) {
        this.posts = posts;
        this.nextOffset = nextOffset;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public int getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }
}
