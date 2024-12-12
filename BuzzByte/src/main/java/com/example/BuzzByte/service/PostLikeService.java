package com.example.BuzzByte.service;

import com.example.BuzzByte.model.likes.PostLike;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PostLikeService {

    @Transactional
    List<PostLike> getAllLikes();
    @Transactional
    void deleteLike(Long likeId);
    @Transactional
    List<PostLike> getLikesByPost(Long postId);
    //List<PostLike> getLikesByUser(Long userId);
    @Transactional
    PostLike addLike(PostLike like);
    @Transactional
    PostLike getLike(Long likeId);
    boolean isPostLikedByUser(Long postId, Long userId);
}
