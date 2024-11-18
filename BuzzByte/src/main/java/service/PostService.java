package service;

import model.Post;
import model.responses.GetPostsResponse;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> getPosts(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);

        return postRepository.findAll(pageable);
    }
}
