package com.example.BuzzByte.service;

import com.example.BuzzByte.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Post addPost(Post post);
    Post updatePost(Post post);
    void deletePost(long postId);
    Post getPost(long postId);
    Post getPost(String postTitle);
    List<Post> getAllPosts();   // deprecated?
    Page<Post> getPosts(int pageNumber, int pageSize);
    Page<Post> findAllByCriteria(Long postId, String postTitle, String postContent, String postAuthor, String postCategory, Pageable pageable);
}
