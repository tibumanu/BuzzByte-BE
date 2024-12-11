package com.example.BuzzByte.service;

import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.utils.dto.PostDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {
    @Transactional
    Post addPost(Post post);
    @Transactional
    Post updatePost(Post post);
    @Transactional
    void deletePost(long postId);
    @Transactional
    Post getPost(long postId);
    //@Transactional
    //PostDto getPostDto(long postId);
    @Transactional
    Post getPost(String postTitle);
    @Transactional
    List<Post> getAllPosts();   // deprecated?
    @Transactional
    Page<Post> getPosts(int pageNumber, int pageSize);
    @Transactional
    Page<Post> findAllByCriteria(Long postId, String postTitle, String postContent, String postAuthor, List<String> postTags, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    //@Transactional
    //Page<Post> findAllByCriteriaWrapper(Long postId, String postTitle, String postContent, String postAuthor, String postCategory, Pageable pageable);
}
