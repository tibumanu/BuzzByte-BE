package com.example.BuzzByte.repository;

import com.example.BuzzByte.model.likes.PostLike;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends LikeRepository<PostLike> {
    public List<PostLike> findAll();
    public PostLike findById(long id);
    // method to return all likes for a post
    public List<PostLike> findPostLikeByPostId(long postId);
    // method to return all likes for a user
    public List<PostLike> findPostLikeByUserId(long userId);
    boolean existsByPostIdAndUserId(Long postId, Long userId);
}
